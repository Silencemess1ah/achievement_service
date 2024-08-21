package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.events.CommentEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ThousandCommentsHandlerTest {
    @Mock
    private AchievementCache cache;
    @Mock
    private AchievementService service;
    @InjectMocks
    private ThousandCommentsHandler handler;

    @Test
    public void testHandle(){
        long userId = 1L;
        long achievementId = 2L;
        AchievementProgress progress = new AchievementProgress();
        progress.setUserId(userId);
        progress.setId(achievementId);
        Achievement achievement = new Achievement();
        achievement.setId(achievementId);
        achievement.setTitle("Title");
        achievement.setPoints(1);
        CommentEvent event = new CommentEvent(1L,1L,1L,"Test");
        when(cache.get(any())).thenReturn(achievement);
        when(service.hasAchievement(userId, achievementId)).thenReturn(false);
        when(service.getProgress(userId,achievementId)).thenReturn(new AchievementProgress());
        handler.handle(event);
        verify(service).giveAchievement(userId,achievementId);
    }
}
