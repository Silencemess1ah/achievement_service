package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.service.filter.AchievementFilter;
import faang.school.achievement.service.filter.impl.AchievementTitleFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Test
    void getAllAchievement_ShouldReturnAllAchievements() {
        // Arrange
        Achievement achievement1 = mock(Achievement.class);
        Achievement achievement2 = mock(Achievement.class);
        List<Achievement> achievements = List.of(achievement1, achievement2);

        AchievementDto achievementDto1 = mock(AchievementDto.class);
        AchievementDto achievementDto2 = mock(AchievementDto.class);
        List<AchievementDto> achievementDtos = List.of(achievementDto1, achievementDto2);

        when(achievementRepository.findAll()).thenReturn(achievements);
        when(achievementMapper.toListDto(achievements)).thenReturn(achievementDtos);

        // Act
        List<AchievementDto> result = achievementService.getAllAchievement();

        // Assert
        assertEquals(achievementDtos, result);
        verify(achievementRepository, times(1)).findAll();
        verify(achievementMapper, times(1)).toListDto(achievements);
    }

    @Test
    void getAchievementById_ShouldReturnAchievement_WhenAchievementExists() {
        // Arrange
        long achievementId = 1L;
        Achievement achievement = mock(Achievement.class);
        AchievementDto achievementDto = mock(AchievementDto.class);

        when(achievementRepository.findById(achievementId)).thenReturn(Optional.of(achievement));
        when(achievementMapper.toDto(achievement)).thenReturn(achievementDto);

        // Act
        AchievementDto result = achievementService.getAchievementById(achievementId);

        // Assert
        assertEquals(achievementDto, result);
        verify(achievementRepository, times(1)).findById(achievementId);
        verify(achievementMapper, times(1)).toDto(achievement);
    }

    @Test
    void getAchievementById_ShouldThrowEntityNotFoundException_WhenAchievementDoesNotExist() {
        // Arrange
        long achievementId = 1L;

        when(achievementRepository.findById(achievementId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> achievementService.getAchievementById(achievementId));
        verify(achievementRepository, times(1)).findById(achievementId);
        verify(achievementMapper, never()).toDto(any(Achievement.class));
    }

    @Test
    void getAchievementByFilter_ShouldApplyFiltersAndReturnFilteredAchievements() {
        // Arrange
        AchievementFilterDto filterDto = mock(AchievementFilterDto.class);
        Achievement achievement1 = mock(Achievement.class);
        Achievement achievement2 = mock(Achievement.class);
        List<Achievement> achievements = List.of(achievement1, achievement2);

        AchievementDto achievementDto1 = mock(AchievementDto.class);
        List<AchievementDto> achievementDtos = List.of(achievementDto1);

        when(achievementRepository.findAll()).thenReturn(achievements);
        when(achievementFilter.get(0).isApplicable(filterDto)).thenReturn(true);
        when(achievementFilter.get(0).apply(achievements, filterDto)).thenReturn(Stream.of(achievement1));
        when(achievementMapper.toDto(achievement1)).thenReturn(achievementDto1);

        // Act
        List<AchievementDto> result = achievementService.getAchievementByFilter(filterDto);

        // Assert
        assertEquals(achievementDtos, result);
        verify(achievementRepository, times(1)).findAll();
        verify(achievementFilter.get(0), times(1)).isApplicable(filterDto);
        verify(achievementFilter.get(0), times(1)).apply(achievements, filterDto);
        verify(achievementMapper, times(1)).toDto(achievement1);
    }

    @Test
    void getAchievementByFilter_ShouldReturnEmptyList_WhenNoAchievementsMatchFilter() {
        // Arrange
        AchievementFilterDto filterDto = mock(AchievementFilterDto.class);
        List<Achievement> achievements = List.of(mock(Achievement.class));

        when(achievementRepository.findAll()).thenReturn(achievements);
        when(achievementFilter.get(0).isApplicable(filterDto)).thenReturn(true);
        when(achievementFilter.get(0).apply(achievements, filterDto)).thenReturn(Stream.empty());

        // Act
        List<AchievementDto> result = achievementService.getAchievementByFilter(filterDto);

        // Assert
        assertEquals(Collections.emptyList(), result);
        verify(achievementRepository, times(1)).findAll();
        verify(achievementFilter.get(0), times(1)).isApplicable(filterDto);
        verify(achievementFilter.get(0), times(1)).apply(achievements, filterDto);
        verify(achievementMapper, never()).toDto(any());
    }
}