package faang.school.achievement.service.eventHandler;

import faang.school.achievement.dto.FollowerEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FollowerEventHandlerTest {
    @Mock
    protected AchievementCache achievementCache;
    @Mock
    protected AchievementService achievementService;
    @Captor
    private ArgumentCaptor<AchievementProgress> progressCaptor;

    private FollowerEventHandler followerEventHandler;
    private long followeeId, achievementId;
    private Achievement achievement;
    private String achievementTitle;
    private AchievementProgress progress;
    private FollowerEvent event;

    private static class TestImplementation extends FollowerEventHandler {
        public TestImplementation(AchievementCache cache, AchievementService achievementService) {
            super(cache, achievementService);
        }

        @Override
        public void handle(FollowerEvent event) {
        }
    }

    @BeforeEach
    void setUp() {
        followerEventHandler = new TestImplementation(achievementCache, achievementService);
        followeeId = 10L;
        achievementId = 1L;
        achievementTitle = "title";
        achievement = Achievement.builder()
                .id(achievementId)
                .title(achievementTitle)
                .points(3)
                .build();
        progress = AchievementProgress.builder()
                .currentPoints(1)
                .achievement(achievement)
                .build();
        event = FollowerEvent.builder()
                .followeeId(followeeId)
                .build();
    }

    @Test
    void testHandleWhenGainingProgress() {
        when(achievementCache.get(achievementTitle)).thenReturn(achievement);
        when(achievementService.hasAchievement(followeeId, achievementId)).thenReturn(false);
        when(achievementService.getProgress(followeeId, achievementId)).thenReturn(progress);
        long expectedPoints = progress.getCurrentPoints() + 1;

        followerEventHandler.process(event, achievementTitle);
        verify(achievementService, times(1)).createProgressIfNecessary(followeeId, achievementId);
        verify(achievementService, times(1)).getProgress(followeeId, achievementId);
        verify(achievementService, times(1)).saveProgress(progressCaptor.capture());
        AchievementProgress updatedProgress = progressCaptor.getValue();
        assertEquals(expectedPoints, updatedProgress.getCurrentPoints());
        verify(achievementService, times(0)).giveAchievement(followeeId, achievement);
    }

    @Test
    void testHandleWhenAchievementReceiving() {
        when(achievementCache.get(achievementTitle)).thenReturn(achievement);
        when(achievementService.hasAchievement(followeeId, achievementId)).thenReturn(false);
        when(achievementService.getProgress(followeeId, achievementId)).thenReturn(progress);
        progress.setCurrentPoints(achievement.getPoints() - 1);
        long expectedPoints = progress.getCurrentPoints() + 1;

        followerEventHandler.process(event, achievementTitle);
        verify(achievementService, times(1)).createProgressIfNecessary(followeeId, achievementId);
        verify(achievementService, times(1)).getProgress(followeeId, achievementId);
        verify(achievementService, times(1)).saveProgress(progressCaptor.capture());
        AchievementProgress updatedProgress = progressCaptor.getValue();
        assertEquals(expectedPoints, updatedProgress.getCurrentPoints());
        verify(achievementService, times(1)).giveAchievement(followeeId, achievement);
    }

    @Test
    void testHandleWhenAchievementAlreadyReceived() {
        when(achievementCache.get(achievementTitle)).thenReturn(achievement);
        when(achievementService.hasAchievement(followeeId, achievementId)).thenReturn(true);

        followerEventHandler.process(event, achievementTitle);
        verify(achievementService, times(0)).createProgressIfNecessary(anyLong(), anyLong());
        verify(achievementService, times(0)).getProgress(anyLong(), anyLong());
        verify(achievementService, times(0)).saveProgress(any());
        verify(achievementService, times(0)).giveAchievement(anyLong(), any());
    }
}