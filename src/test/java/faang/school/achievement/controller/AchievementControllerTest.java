package faang.school.achievement.controller;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.test_data.TestDataAchievement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementControllerTest {
    @Mock
    private AchievementService achievementService;
    @Mock
    private AchievementMapper achievementMapper;
    @Mock
    private UserAchievementMapper userAchievementMapper;
    @Mock
    private AchievementProgressMapper achievementProgressMapper;
    @InjectMocks
    private AchievementController achievementController;

    private Achievement achievement;
    private AchievementDto achievementDto;
    private AchievementFilterDto filterDto;
    private UserAchievement userAchievement;
    private UserAchievementDto userAchievementDto;
    private AchievementProgress achievementProgress;
    private AchievementProgressDto achievementProgressDto;

    @BeforeEach
    void setUp() {
        TestDataAchievement testDataAchievement = new TestDataAchievement();
        achievement = testDataAchievement.getAchievement();
        achievementDto = testDataAchievement.getAchievementDto();
        filterDto = testDataAchievement.getAchievementFilterDto();
        userAchievement = testDataAchievement.getUserAchievement();
        userAchievementDto = testDataAchievement.getUserAchievementDto();
        achievementProgress = testDataAchievement.getAchievementProgress();
        achievementProgressDto = testDataAchievement.getAchievementProgressDto();
    }

    @Test
    void testGetAchievementsByFilter_Success() {
        List<Achievement> achievements = List.of(achievement);
        List<AchievementDto> achievementDtoList = List.of(achievementDto);

        when(achievementService.getAchievementByFilters(filterDto)).thenReturn(achievements);
        when(achievementMapper.toDtoList(achievements)).thenReturn(achievementDtoList);

        List<AchievementDto> result = achievementController.getAchievementsByFilter(filterDto);

        assertNotNull(result);
        assertEquals(achievementDtoList, result);
    }

    @Test
    void testGetAchievementById_Success() {
        when(achievementService.getAchievementById(achievement.getId())).thenReturn(achievement);
        when(achievementMapper.toDto(achievement)).thenReturn(achievementDto);

        AchievementDto result = achievementController.getAchievementById(achievement.getId());

        assertNotNull(result);
        assertEquals(achievementDto, result);
    }

    @Test
    void testGetUserAchievements_Success() {
        List<UserAchievement> userAchievements = List.of(userAchievement);
        List<UserAchievementDto> userAchievementsDtoList = List.of(userAchievementDto);

        when(achievementService.getUserAchievements(userAchievement.getUserId())).thenReturn(userAchievements);
        when(userAchievementMapper.toDtoList(userAchievements)).thenReturn(userAchievementsDtoList);

        List<UserAchievementDto> result = achievementController.getUserAchievements(userAchievement.getUserId());

        assertNotNull(result);
        assertEquals(userAchievementsDtoList, result);
    }

    @Test
    void testGetUserProgress_Success() {
        List<AchievementProgress> achievementProgresses = List.of(achievementProgress);
        List<AchievementProgressDto> achievementProgressDtoList = List.of(achievementProgressDto);

        when(achievementService.getUserProgress(achievementProgress.getUserId())).thenReturn(achievementProgresses);
        when(achievementProgressMapper.toDtoList(achievementProgresses)).thenReturn(achievementProgressDtoList);

        List<AchievementProgressDto> result = achievementController.getUserProgress(achievementProgress.getUserId());

        assertNotNull(result);
        assertEquals(achievementProgressDtoList, result);
    }
}
