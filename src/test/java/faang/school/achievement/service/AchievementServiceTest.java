package faang.school.achievement.service;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.filter.AchievementFilter;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AchievementServiceTest {

    @InjectMocks
    private AchievementService achievementService;

    @Mock
    private AchievementRepository achievementRepository;

    @Mock
    private UserAchievementRepository userAchievementRepository;

    @Mock
    private AchievementProgressRepository achievementProgressRepository;

    @Mock
    private List<AchievementFilter> achievementFilters;

    @Mock
    private AchievementMapper achievementMapper;

    @Mock
    private AchievementProgressMapper achievementProgressMapper;

    @Mock
    private AchievementCache achievementCache;

    @Mock
    private AchievementFilter filter;

    private AchievementDto achievementDto;
    private Achievement achievement;
    private Achievement secondAchievement;
    private AchievementFilterDto achievementFilterDto;
    private Long userId;
    private List<UserAchievement> userAchievements;
    private AchievementProgress progress;
    private AchievementProgress secondProgress;
    private List<AchievementProgress> progresses;
    private AchievementProgressDto progressDto;
    private AchievementProgressDto secondProgressDto;

    @BeforeEach
    void setUp() {
        userId = 1L;
        achievementDto = AchievementDto.builder()
                .id(1L)
                .title("Achievement1")
                .description("description")
                .rarity(Rarity.COMMON)
                .points(2L)
                .build();
        achievement = Achievement.builder()
                .id(1L)
                .title("Achievement1")
                .description("description")
                .rarity(Rarity.COMMON)
                .points(2L)
                .build();
        secondAchievement = Achievement.builder()
                .id(2L)
                .title("Achievement2")
                .description("secondDescription")
                .rarity(Rarity.COMMON)
                .points(2L)
                .build();
        UserAchievement userAchievement = UserAchievement.builder()
                .userId(1L)
                .achievement(achievement)
                .build();
        UserAchievement secondUserAchievement = UserAchievement.builder()
                .userId(1L)
                .achievement(achievement)
                .build();
        achievementFilterDto = AchievementFilterDto.builder()
                .descriptionPrefix("desc")
                .build();
        userAchievements = List.of(userAchievement, secondUserAchievement);
        progress = AchievementProgress.builder()
                .id(1L)
                .userId(1L)
                .achievement(achievement)
                .createdAt(LocalDateTime.now())
                .build();
        secondProgress = AchievementProgress.builder()
                .id(2L)
                .userId(1L)
                .achievement(secondAchievement)
                .createdAt(LocalDateTime.now())
                .build();
        progresses = List.of(progress, secondProgress);

        progressDto = AchievementProgressDto.builder()
                .id(1L)
                .achievement(achievement)
                .userId(userId)
                .currentPoints(1L)
                .build();
        secondProgressDto = AchievementProgressDto.builder()
                .id(2L)
                .achievement(achievement)
                .userId(userId)
                .currentPoints(2L)
                .build();

    }

    @Test
    void testGetAchievementsSuccess() {
        //given & when
        when(achievementCache.getAll()).thenReturn(List.of(achievement, secondAchievement));
        when(filter.isApplicable(achievementFilterDto)).thenReturn(true);
        when(filter.apply(any(Stream.class), eq(achievementFilterDto))).thenReturn(Stream.of(achievement));
        when(achievementFilters.stream()).thenReturn(Stream.of(filter));

        when(achievementMapper.toDto(achievement)).thenReturn(achievementDto);


        List<AchievementDto> result = achievementService.getAchievements(achievementFilterDto);

        //then
        assertEquals(1, result.size());
        assertEquals(achievementDto, result.get(0));

        verify(achievementCache, times(1)).getAll();
        verify(achievementMapper, times(1)).toDto(achievement);
    }


    @Test
    void testGetAchievementsWhenFiltersIsNull() {
        //when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            achievementService.getAchievements(null);
        });
        assertEquals("Achievement filter is null", exception.getMessage());
    }

    @Test
    void testGetUserAchievements() {
        //given & when
        when(userAchievementRepository.findByUserId(userId)).thenReturn(userAchievements);
        when(achievementMapper.toDto(achievement)).thenReturn(achievementDto);

        List<AchievementDto> result = achievementService.getUserAchievements(userId);

        //then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(achievementDto, result.get(0));

        verify(userAchievementRepository, times(1)).findByUserId(userId);
        verify(achievementMapper, times(2)).toDto(achievement);
    }

    @Test
    void testGetAchievement() {
        //given & when
        Long achievementId = 1L;
        when(achievementRepository.findById(achievementId)).thenReturn(Optional.of(achievement));
        when(achievementMapper.toDto(achievement)).thenReturn(achievementDto);

        AchievementDto result = achievementService.getAchievement(achievementId);

        //then
        assertNotNull(result);
        assertEquals(achievementDto, result);

        verify(achievementRepository, times(1)).findById(achievementId);
        verify(achievementMapper, times(1)).toDto(achievement);
    }

    @Test
    void testGetAchievementNotFound() {
        //given
        Long achievementId = 1L;
        when(achievementRepository.findById(achievementId)).thenReturn(Optional.empty());

        //when & then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            achievementService.getAchievement(achievementId);
        });

        assertEquals("Achievement not found", thrown.getMessage());
        verify(achievementRepository, times(1)).findById(achievementId);
    }

    @Test
    void testGetUnfinishedAchievements() {
        //given & when
        when(userAchievementRepository.findByUserId(userId)).thenReturn(userAchievements);
        when(achievementProgressRepository.findByUserId(userId)).thenReturn(progresses);
        when(achievementProgressMapper.toDto(secondProgress)).thenReturn(secondProgressDto);

        List<AchievementProgressDto> result = achievementService.getUnfinishedAchievements(userId);

        //then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(secondProgressDto, result.get(0));

        verify(userAchievementRepository, times(1)).findByUserId(userId);
        verify(achievementProgressRepository, times(1)).findByUserId(userId);
        verify(achievementProgressMapper, times(1)).toDto(secondProgress);
    }
}