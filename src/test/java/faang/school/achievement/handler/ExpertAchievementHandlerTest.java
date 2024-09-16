package faang.school.achievement.handler;

import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.eventhandler.ExpertAchievementHandler;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.CommentEvent;
import faang.school.achievement.service.achievement.AchievementService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ExpertAchievementHandlerTest {
    @Mock
    AchievementCache achievementCache;
    @Mock
    AchievementService achievementService;
    @InjectMocks
    ExpertAchievementHandler expertAchievementHandler;

    private AchievementProgress achievementProgress;
    private CommentEvent commentEvent;
    private Achievement achievement;
    private long achievementId;
    private long userId;

    @BeforeEach
    void setUp() {
        achievementId = 4L;
        userId = 1L;
        commentEvent = new CommentEvent(1L, 2L, 3L, "something");
        achievement = Achievement.builder()
                .id(achievementId)
                .title("EXPERT")
                .points(100)
                .build();
        achievementProgress = AchievementProgress.builder()
                .id(5L)
                .userId(1L)
                .achievement(achievement)
                .currentPoints(0)
                .build();
    }

    @Test
    public void testHandleEventWithObtainedAchievement() {
        Mockito.when(achievementCache.get("EXPERT")).thenReturn(achievement);
        Mockito.when(achievementService.hasAchievement(userId, achievementId)).thenReturn(true);
        expertAchievementHandler.handle(commentEvent);
        Mockito.verify(achievementService).hasAchievement(userId, achievementId);
        Mockito.verify(achievementService, Mockito.never()).createProgressIfNecessary(userId, achievementId);
        Mockito.verify(achievementService, Mockito.never()).getProgress(userId, achievementId);
        Mockito.verify(achievementService, Mockito.never()).giveAchievement(userId, achievement);
    }

    @Test
    public void testHandleEventWithIncrement() {
        Mockito.when(achievementCache.get("EXPERT")).thenReturn(achievement);
        Mockito.when(achievementService.hasAchievement(userId, achievementId)).thenReturn(false);
        Mockito.when(achievementService.getProgress(userId, achievementId)).thenReturn(achievementProgress);
        Mockito.doNothing().when(achievementService).createProgressIfNecessary(userId, achievementId);
        Mockito.doNothing().when(achievementService).updateProgress(achievementProgress);
        expertAchievementHandler.handle(commentEvent);
        Mockito.verify(achievementService).updateProgress(achievementProgress);
        Mockito.verify(achievementService).createProgressIfNecessary(userId, achievementId);
        Mockito.verify(achievementService).hasAchievement(userId, achievementId);
        Mockito.verify(achievementService).getProgress(userId, achievementId);
        Mockito.verify(achievementService, Mockito.never()).giveAchievement(userId, achievement);
        Assert.assertEquals(1, achievementProgress.getCurrentPoints());
    }

    @Test
    public void testHandleEventWithAchievement() {
        achievementProgress.setCurrentPoints(99);
        Mockito.when(achievementCache.get("EXPERT")).thenReturn(achievement);
        Mockito.when(achievementService.hasAchievement(userId, achievementId)).thenReturn(false);
        Mockito.when(achievementService.getProgress(userId, achievementId)).thenReturn(achievementProgress);
        Mockito.doNothing().when(achievementService).createProgressIfNecessary(userId, achievementId);
        Mockito.doNothing().when(achievementService).updateProgress(achievementProgress);
        Mockito.doNothing().when(achievementService).giveAchievement(userId, achievement);
        expertAchievementHandler.handle(commentEvent);
        Mockito.verify(achievementService).updateProgress(achievementProgress);
        Mockito.verify(achievementService).createProgressIfNecessary(userId, achievementId);
        Mockito.verify(achievementService).hasAchievement(userId, achievementId);
        Mockito.verify(achievementService).getProgress(userId, achievementId);
        Mockito.verify(achievementService).giveAchievement(userId, achievement);
        Assert.assertEquals(100, achievementProgress.getCurrentPoints());
    }
}
