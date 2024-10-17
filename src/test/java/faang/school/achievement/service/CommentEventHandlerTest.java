package faang.school.achievement.service;

import faang.school.achievement.dto.CommentEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.service.cache.CacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentEventHandlerTest {

    @InjectMocks
    private CommentEventHandler commentEventHandler;

    @Mock
    private AchievementService achievementService;

    @Mock
    AchievementProgressRepository achievementProgressRepository;

    @Mock
    CacheService<Achievement> cacheService;

    private CommentEvent commentEvent;
    private Achievement achievement;
    private AchievementProgress achievementProgress;

    @BeforeEach
    public void setUp() {
        commentEvent = new CommentEvent();
        commentEvent.setIdPost(1L);
        commentEvent.setIdAuthor(1L);

        achievement = new Achievement();
        achievement.setId(1L);
        achievement.setTitle("EXPERT");
        achievement.setPoints(10);

        achievementProgress = new AchievementProgress();
        achievementProgress.setCurrentPoints(5L);
        achievementProgress.setAchievement(achievement);
    }

    @Test
    public void testHandleEvent_ProgressUpdate() {
        when(cacheService.getCacheValue("EXPERT", Achievement.class)).thenReturn(achievement);
        when(achievementService.hasAchievement(commentEvent.getIdAuthor(), achievement.getId())).thenReturn(false);
        when(achievementService.getProgress(commentEvent.getIdAuthor(), achievement.getId())).thenReturn(achievementProgress);

        commentEventHandler.handleEvent(commentEvent);

        verify(achievementService, times(1)).createProgressIfNecessary(commentEvent.getIdAuthor(), achievement.getId());
        verify(achievementService, times(1)).getProgress(commentEvent.getIdAuthor(), achievement.getId());
        verify(achievementProgressRepository, times(1)).save(achievementProgress);

        assertEquals(6, achievementProgress.getCurrentPoints());
    }

    @Test
    public void testHandleEvent_AchievementGiven() {
        achievementProgress.setCurrentPoints(10L);
        when(cacheService.getCacheValue("EXPERT", Achievement.class)).thenReturn(achievement);
        when(achievementService.hasAchievement(commentEvent.getIdAuthor(), achievement.getId())).thenReturn(false);
        when(achievementService.getProgress(commentEvent.getIdAuthor(), achievement.getId())).thenReturn(achievementProgress);

        commentEventHandler.handleEvent(commentEvent);

        verify(achievementService, times(1)).giveAchievement(commentEvent.getIdAuthor(), achievement);
        verify(achievementProgressRepository, never()).save(any(AchievementProgress.class));
    }
}
