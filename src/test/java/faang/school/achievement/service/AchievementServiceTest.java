package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.filter.achievement.AchievementFilter;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {

    @Mock
    AchievementRepository achievementRepository;

    @Mock
    UserAchievementRepository userAchievementRepository;

    @Mock
    AchievementProgressRepository achievementProgressRepository;

    @Mock
    AchievementMapper achievementMapper;

    @Mock
    UserAchievementMapper userAchievementMapper;

    @Mock
    AchievementProgressMapper achievementProgressMapper;

    @Mock
    AchievementFilter achievementFilter;

    @InjectMocks
    AchievementService achievementService;

    long userId;
    long achievementId;
    Achievement achievement;
    AchievementDto achievementDto;
    AchievementFilterDto achievementFilterDto;
    UserAchievement userAchievement;
    UserAchievementDto userAchievementDto;
    AchievementProgress achievementProgress;
    AchievementProgressDto achievementProgressDto;
    List<Achievement> achievements;
    List<AchievementDto> achievementsDto;
    List<UserAchievement> userAchievements;
    List<UserAchievementDto> userAchievementsDto;
    List<AchievementProgress> achievementProgresses;
    List<AchievementProgressDto> achievementProgressesDto;

    @BeforeEach
    void setUp() {
        achievementService = new AchievementService(
            achievementRepository,
            userAchievementRepository,
            achievementProgressRepository,
            achievementMapper,
            userAchievementMapper,
            achievementProgressMapper,
            List.of(achievementFilter)
        );

        userId = 1;
        achievementId = 1;
        achievement = new Achievement();
        achievementDto = AchievementDto.builder().build();
        achievementFilterDto = AchievementFilterDto.builder().build();
        userAchievement = new UserAchievement();
        userAchievementDto = UserAchievementDto.builder().build();
        achievementProgress = new AchievementProgress();
        achievementProgressDto = AchievementProgressDto.builder().build();
        achievements = List.of(achievement);
        achievementsDto = List.of(achievementDto);
        userAchievements = List.of(userAchievement);
        userAchievementsDto = List.of(userAchievementDto);
        achievementProgresses = List.of(achievementProgress);
        achievementProgressesDto = List.of(achievementProgressDto);
    }

    @Test
    @DisplayName("Should return list of AchievementDto when filtering achievements by filter")
    void getAchievementsByFilter() {
        when(achievementRepository.findAll()).thenReturn(achievements);
        when(achievementFilter.isApplicable(achievementFilterDto)).thenReturn(true);
        when(achievementFilter.apply(any(), any())).thenReturn(achievements.stream());
        when(achievementMapper.toDto(achievement)).thenReturn(achievementDto);

        List<AchievementDto> result = achievementService.getAchievementsByFilter(achievementFilterDto);

        verify(achievementRepository).findAll();
        verify(achievementFilter).isApplicable(achievementFilterDto);
        verify(achievementFilter).apply(any(), any());
        verify(achievementMapper).toDto(achievement);
        assertNotNull(result);
        assertEquals(achievementsDto, result);
    }

    @Test
    @DisplayName("Should return list of UserAchievementDto when retrieving achievements by user ID")
    void getAchievementsByUserId() {
        when(userAchievementRepository.findByUserId(userId)).thenReturn(userAchievements);
        when(userAchievementMapper.toDto(userAchievement)).thenReturn(userAchievementDto);

        List<UserAchievementDto> result = achievementService.getAchievementsByUserId(userId);

        verify(userAchievementRepository).findByUserId(userId);
        verify(userAchievementMapper).toDto(userAchievement);
        assertNotNull(result);
        assertEquals(userAchievementsDto, result);
    }

    @Test
    @DisplayName("Should return AchievementDto when retrieving achievement by ID")
    void getAchievementById() {
        when(achievementRepository.getById(achievementId)).thenReturn(achievement);
        when(achievementMapper.toDto(achievement)).thenReturn(achievementDto);

        AchievementDto result = achievementService.getAchievementById(achievementId);

        verify(achievementRepository).getById(achievementId);
        verify(achievementMapper).toDto(achievement);
        assertNotNull(result);
        assertEquals(achievementDto, result);
    }

    @Test
    @DisplayName("Should return list of AchievementProgressDto when retrieving achievement progress by user ID")
    void getAchievementProgressByUserId() {
        when(achievementProgressRepository.findByUserId(userId)).thenReturn(achievementProgresses);
        when(achievementProgressMapper.toDto(achievementProgress)).thenReturn(achievementProgressDto);

        List<AchievementProgressDto> result = achievementService.getAchievementProgressByUserId(userId);

        verify(achievementProgressRepository).findByUserId(userId);
        verify(achievementProgressMapper).toDto(achievementProgress);
        assertNotNull(result);
        assertEquals(achievementProgressesDto, result);
    }
}