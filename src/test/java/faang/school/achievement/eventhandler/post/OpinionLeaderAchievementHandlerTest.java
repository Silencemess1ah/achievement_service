package faang.school.achievement.eventhandler.post;

import faang.school.achievement.event.post.PostEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.achievement.AchievementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OpinionLeaderAchievementHandlerTest {
    @Mock
    private AchievementService achievementService;
    @Mock
    private AchievementCache cache;
    @InjectMocks
    private OpinionLeaderAchievementHandler handler;
    private String achievementName = "OPINION_LEADER";
    private long userId = 1L;
    private long achievementId = 2L;
    private Achievement achievement = Achievement.builder()
            .id(achievementId)
            .title(achievementName)
            .points(1000)
            .build();
    private PostEvent event = PostEvent.builder()
            .authorId(userId)
            .build();
    private AchievementProgress progress = new AchievementProgress();

    @BeforeEach
    void setUp() {
        when(cache.get(achievementName)).thenReturn(achievement);
    }
    @Test
    public void testHandleHasAchievement() {
        when(achievementService.hasAchievement(userId, achievementId)).thenReturn(true);

        handler.handle(event);

        verify(achievementService, times(0)).createProgressIfNecessary(anyLong(), anyLong());
    }

    @Test
    public void testHandleWithNotEnoughPoints() {
        when(achievementService.getProgress(userId, achievementId)).thenReturn(progress);

        handler.handle(event);

        verify(achievementService).createProgressIfNecessary(userId, achievementId);
        assertEquals(1, progress.getCurrentPoints());
        verify(achievementService).updateProgress(progress);
        verify(achievementService, times(0)).giveAchievement(any(), any());
    }

    @Test
    public void testHandleWithEnoughPoints() {
        progress.setCurrentPoints(999);
        when(achievementService.getProgress(userId, achievementId)).thenReturn(progress);

        handler.handle(event);

        verify(achievementService).createProgressIfNecessary(userId, achievementId);
        assertEquals(1000, progress.getCurrentPoints());
        verify(achievementService).updateProgress(progress);
        verify(achievementService).giveAchievement(userId, achievement);
    }
}
