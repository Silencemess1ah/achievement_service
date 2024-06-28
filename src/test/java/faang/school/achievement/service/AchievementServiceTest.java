package faang.school.achievement.service;

import faang.school.achievement.jpa.AchievementProgressJpaRepository;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AchievementServiceTest {

    private static final long USER_ID = 1L;
    private static final long ACHIEVE_ID = 2L;

    @InjectMocks
    private AchievementService achievementService;
    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Mock
    private AchievementProgressJpaRepository achievementProgressJpaRepository;
    @Mock
    private AchievementProgressRepository achievementProgressRepository;

    @Test
    @DisplayName("There is an achievement.")
    public void testHasAchievement() {
        when(userAchievementRepository.existsByUserIdAndAchievementId(USER_ID, ACHIEVE_ID)).thenReturn(true);
        boolean result = achievementService.hasAchievement(USER_ID, ACHIEVE_ID);

        assertTrue(result);
        verify(userAchievementRepository, times(1)).existsByUserIdAndAchievementId(USER_ID, ACHIEVE_ID);
    }

    @Test
    @DisplayName("No achievement.")
    public void testNotHasAchievement() {
        when(userAchievementRepository.existsByUserIdAndAchievementId(USER_ID, ACHIEVE_ID)).thenReturn(false);
        boolean result = achievementService.hasAchievement(USER_ID, ACHIEVE_ID);

        assertFalse(result);
        verify(userAchievementRepository, times(1)).existsByUserIdAndAchievementId(USER_ID, ACHIEVE_ID);
    }

    @Test
    @DisplayName("Making progress.")
    public void testCreateProgressIfNecessary() {
        achievementService.createProgressIfNecessary(USER_ID, ACHIEVE_ID);

        verify(achievementProgressJpaRepository, times(1)).createProgressIfNecessary(USER_ID, ACHIEVE_ID);
    }

    @Test
    @DisplayName("Getting progress.")
    public void testGetProgress() {
        AchievementProgress expectedProgress = new AchievementProgress();
        when(achievementProgressRepository.findByUserIdAndAchievementId(USER_ID, ACHIEVE_ID)).thenReturn(expectedProgress);

        AchievementProgress actualProgress = achievementService.getProgress(USER_ID, ACHIEVE_ID);

        assertEquals(expectedProgress, actualProgress);
        verify(achievementProgressRepository, times(1)).findByUserIdAndAchievementId(USER_ID, ACHIEVE_ID);
    }

    @Test
    @DisplayName("Give achievement.")
    public void testGiveAchievement() {
        Achievement achievement = new Achievement();
        achievement.setTitle("test");

        achievementService.giveAchievement(USER_ID, achievement);

        ArgumentCaptor<UserAchievement> captor = ArgumentCaptor.forClass(UserAchievement.class);
        verify(userAchievementRepository, times(1)).save(captor.capture());

        UserAchievement capturedUserAchievement = captor.getValue();
        assertEquals(USER_ID, capturedUserAchievement.getUserId());
        assertEquals(achievement, capturedUserAchievement.getAchievement());
    }

    @Test
    @DisplayName("Updating progress.")
    public void testUpdateProgress() {
        AchievementProgress progress = new AchievementProgress();

        achievementService.updateProgress(progress);

        verify(achievementProgressJpaRepository, times(1)).save(progress);
    }
}
