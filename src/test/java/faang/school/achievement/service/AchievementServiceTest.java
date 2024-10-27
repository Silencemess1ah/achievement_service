package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.AchievementTitle;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.filter.AchievementFilter;
import faang.school.achievement.test_data.TestDataAchievement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {

    @Mock
    private AchievementRepository achievementRepository;

    @Mock
    private UserAchievementRepository userAchievementRepository;

    @Mock
    private AchievementProgressRepository achievementProgressRepository;

    @Mock
    private List<AchievementFilter> achievementFilters;

    @InjectMocks
    private AchievementService achievementService;

    private Long userId;
    private AchievementTitle title;
    private Long requiredPoints;
    private Achievement achievement;
    private AchievementProgress progress;

    private TestDataAchievement testDataAchievement;

    @BeforeEach
    void setUp() {
        userId = 1L;
        title = AchievementTitle.WRITER;
        requiredPoints = 10L;
        achievement = mock(Achievement.class);
        progress = AchievementProgress.builder()
                .achievement(achievement)
                .userId(userId)
                .currentPoints(9)
                .build();

        testDataAchievement = new TestDataAchievement();
    }

    @Test
    void testHasAchievement_True() {
        when(userAchievementRepository.hasAchievement(userId, title)).thenReturn(true);

        boolean result = achievementService.hasAchievement(userId, title);

        assertTrue(result);
        verify(userAchievementRepository, times(1)).hasAchievement(userId, title);
    }

    @Test
    void testHasAchievement_False() {
        achievementService.hasAchievement(userId, title);

        verify(userAchievementRepository, times(1)).hasAchievement(userId, title);
    }

    @Test
    void testUpdateProgress_NotAchieved() {
        progress.setCurrentPoints(8);

        when(achievementProgressRepository.getProgress(userId, title)).thenReturn(Optional.of(progress));
        when(achievementRepository.findByTitle(title)).thenReturn(Optional.of(achievement));

        achievementService.updateProgress(userId, title, requiredPoints);

        assertEquals(9, progress.getCurrentPoints());
        verify(achievementProgressRepository, times(1)).save(progress);
    }

    @Test
    void testUpdateProgress_Achieved() {
        when(achievementProgressRepository.getProgress(userId, title)).thenReturn(Optional.of(progress));
        when(achievementRepository.findByTitle(title)).thenReturn(Optional.of(achievement));

        achievementService.updateProgress(userId, title, requiredPoints);

        assertEquals(10, progress.getCurrentPoints());
        verify(userAchievementRepository, times(1)).save(any(UserAchievement.class));
    }

    @Test
    void testUpdateProgress_CreateNewProgress() {
        when(achievementProgressRepository.getProgress(userId, title)).thenReturn(Optional.empty());
        when(achievementRepository.findByTitle(title)).thenReturn(Optional.of(achievement));

        achievementService.updateProgress(userId, title, requiredPoints);

        verify(achievementProgressRepository, times(1)).save(any(AchievementProgress.class));
    }

    @Test
    void testGetAchievementByFilters_Success() {
        Achievement achievement = testDataAchievement.getAchievement();
        AchievementFilterDto filterDto = testDataAchievement.getAchievementFilterDto();
        List<Achievement> achievements = List.of(achievement);

        when(achievementRepository.findAll()).thenReturn(achievements);

        List<Achievement> result = achievementService.getAchievementByFilters(filterDto);

        assertNotNull(result);
        assertEquals(achievements, result);
    }

    @Test
    void testGetAchievementById_Success() {
        Achievement achievement = testDataAchievement.getAchievement();

        when(achievementRepository.findById(achievement.getId())).thenReturn(Optional.of(achievement));

        Achievement result = achievementService.getAchievementById(achievement.getId());

        assertNotNull(result);
        assertEquals(achievement, result);
    }

    @Test
    void testGetAchievementById_NotFound_throwIllegalArgumentException() {
        Achievement achievement = testDataAchievement.getAchievement();

        when(achievementRepository.findById(achievement.getId())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> achievementService.getAchievementById(achievement.getId())
        );

        assertEquals("Achievement with ID: " + achievement.getId() + " not found.", exception.getMessage());
    }

    @Test
    void testGetUserAchievements_Success() {
        List<UserAchievement> userAchievements = List.of(testDataAchievement.getUserAchievement());

        when(userAchievementRepository.findByUserId(userId)).thenReturn(userAchievements);

        List<UserAchievement> result = achievementService.getUserAchievements(userId);

        assertNotNull(result);
        assertEquals(userAchievements, result);
    }

    @Test
    void testGetUserProgress() {
        List<AchievementProgress> progressList = List.of(testDataAchievement.getAchievementProgress());

        when(achievementProgressRepository.findByUserId(userId)).thenReturn(progressList);

        List<AchievementProgress> result = achievementService.getUserProgress(userId);

        assertNotNull(result);
        assertEquals(progressList, result);
    }
}
