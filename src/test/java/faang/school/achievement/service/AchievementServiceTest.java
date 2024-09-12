package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.SortField;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.exception.BadRequestException;
import faang.school.achievement.filter.achievement.AchievementDescriptionFilter;
import faang.school.achievement.filter.achievement.AchievementFilter;
import faang.school.achievement.filter.achievement.AchievementRarityFilter;
import faang.school.achievement.filter.achievement.AchievementTitleFilter;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {

    @Mock
    private AchievementRepository achievementRepository;

    @Mock
    private AchievementMapper achievementMapper;

    private List<AchievementFilter> achievementFilter;

    private AchievementService achievementService;

    @BeforeEach
    void setUp() {
        AchievementFilter achievementTitleFilter = Mockito.mock(AchievementTitleFilter.class);
        achievementFilter = List.of(achievementTitleFilter);
        achievementService = new AchievementService(achievementRepository, achievementMapper, achievementFilter);
    }

    @DisplayName("Should return all achievements")
    @Test
    void getPageableAchievements_ShouldReturnPagedAchievements() {
        Pageable pageable = PageRequest.of(0, 10);
        Achievement achievement1 = mock(Achievement.class);
        Achievement achievement2 = mock(Achievement.class);
        List<Achievement> achievements = List.of(achievement1, achievement2);
        AchievementDto achievementDto1 = mock(AchievementDto.class);
        AchievementDto achievementDto2 = mock(AchievementDto.class);
        List<AchievementDto> achievementDtos = List.of(achievementDto1, achievementDto2);
        Page<Achievement> pagedAchievements = new PageImpl<>(achievements, pageable, achievements.size());

        when(achievementRepository.findAll(pageable)).thenReturn(pagedAchievements);
        when(achievementMapper.toDto(achievement1)).thenReturn(achievementDto1);
        when(achievementMapper.toDto(achievement2)).thenReturn(achievementDto2);

        Page<AchievementDto> result = achievementService.getPageableAchievements(pageable);

        assertEquals(2, result.getTotalElements());
        assertEquals(achievementDtos, result.getContent());
        verify(achievementRepository, times(1)).findAll(pageable);
    }

    @DisplayName("Should return achievement when it exists by id")
    @Test
    void getAchievementById_ShouldReturnAchievement_WhenAchievementExists() {
        long achievementId = 1L;
        Achievement achievement = mock(Achievement.class);
        AchievementDto achievementDto = mock(AchievementDto.class);

        when(achievementRepository.findById(achievementId)).thenReturn(Optional.of(achievement));
        when(achievementMapper.toDto(achievement)).thenReturn(achievementDto);

        AchievementDto result = achievementService.getAchievementById(achievementId);

        assertEquals(achievementDto, result);
        verify(achievementRepository, times(1)).findById(achievementId);
        verify(achievementMapper, times(1)).toDto(achievement);
    }

    @DisplayName("Should throw EntityNotFoundException when achievement does not exist by id")
    @Test
    void getAchievementById_ShouldThrowEntityNotFoundException_WhenAchievementDoesNotExist() {
        long achievementId = 1L;

        when(achievementRepository.findById(achievementId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> achievementService.getAchievementById(achievementId));
        verify(achievementRepository, times(1)).findById(achievementId);
        verify(achievementMapper, never()).toDto(any(Achievement.class));
    }

    @DisplayName("Should apply filters and return filtered achievements")
    @Test
    void getAchievementByFilter_ShouldApplyFiltersAndReturnFilteredPagedAchievements() {
        AchievementFilterDto filterDto = mock(AchievementFilterDto.class);
        Achievement achievement1 = mock(Achievement.class);
        Achievement achievement2 = mock(Achievement.class);
        List<Achievement> achievements = List.of(achievement1, achievement2);
        Pageable pageable = PageRequest.of(0, 10);
        AchievementDto achievementDto1 = mock(AchievementDto.class);
        List<AchievementDto> achievementDtos = List.of(achievementDto1);
        Page<Achievement> pagedAchievements = new PageImpl<>(achievements, pageable, achievements.size());

        when(achievementRepository.findAll(pageable)).thenReturn(pagedAchievements);
        when(achievementFilter.get(0).isApplicable(filterDto)).thenReturn(true);
        when(achievementFilter.get(0).apply(achievements, filterDto)).thenReturn(Stream.of(achievement1));
        when(achievementMapper.toDto(achievement1)).thenReturn(achievementDto1);

        Page<AchievementDto> result = achievementService.getAchievementByFilter(filterDto, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(achievementDtos, result.getContent());
        verify(achievementRepository, times(1)).findAll(pageable);
        verify(achievementFilter.get(0), times(1)).isApplicable(filterDto);
        verify(achievementFilter.get(0), times(1)).apply(achievements, filterDto);
        verify(achievementMapper, times(1)).toDto(achievement1);
    }

    @DisplayName("Should return empty list when no achievements match the filter")
    @Test
    void getAchievementByFilter_ShouldReturnEmptyPage_WhenNoAchievementsMatchFilter() {
        AchievementFilterDto filterDto = mock(AchievementFilterDto.class);
        Pageable pageable = Pageable.unpaged();
        Page<Achievement> pagedAchievements = new PageImpl<>(Collections.emptyList(), pageable, 0); // Пустой результат

        when(achievementRepository.findAll(pageable)).thenReturn(pagedAchievements);
        when(achievementFilter.get(0).isApplicable(filterDto)).thenReturn(true);
        when(achievementFilter.get(0).apply(pagedAchievements.getContent(), filterDto)).thenReturn(Stream.empty());

        Page<AchievementDto> result = achievementService.getAchievementByFilter(filterDto, pageable);

        assertEquals(0, result.getTotalElements()); // Проверяем, что элементов нет
        verify(achievementRepository, times(1)).findAll(pageable);
        verify(achievementFilter.get(0), times(1)).isApplicable(filterDto);
        verify(achievementFilter.get(0), times(1)).apply(pagedAchievements.getContent(), filterDto);
        verify(achievementMapper, never()).toDto(any());
    }
}