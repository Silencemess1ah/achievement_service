package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.CommentEvent;
import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ExpertAchievementHandlerTest {

    @Mock
    private AchievementCache achievementCache;

    @Mock
    private AchievementService achievementService;

    @InjectMocks
    private ExpertAchievementHandler expertAchievementHandler;

    private Achievement achievement;
    private CommentEvent event;

    @BeforeEach
    void setUp() {
        achievement = Achievement.builder()
                .id(1L)
                .title("EXPERT")
                .points(100L)
                .build();
        event = CommentEvent.builder()
                .commentAuthorId(1L)
                .postAuthorId(2L)
                .postId(3L)
                .commentId(4L)
                .build();
    }

    @Test
    void testCheckHandler() {
        assertTrue(expertAchievementHandler.checkHandler(event));
    }

    @Test
    void testHandleWhenUserDoesNotHaveAchievement() {
        when(achievementCache.get("EXPERT")).thenReturn(Optional.of(achievement));
        when(achievementService.hasAchievement(1L, 1L)).thenReturn(false);
        AchievementProgress progress = new AchievementProgress();
        progress.setCurrentPoints(150L); // Достаточно очков для получения достижения
        when(achievementService.getProgress(1L, 1L)).thenReturn(progress);
        expertAchievementHandler.handle(event);
        verify(achievementService, times(1)).createProgressIfNecessary(1L, 1L);
        verify(achievementService, times(1)).getProgress(1L, 1L);
        verify(achievementService, times(1)).giveAchievement(1L, achievement);
    }

    @Test
    void testHandleWhenUserAlreadyHasAchievement() {
        when(achievementCache.get("EXPERT")).thenReturn(Optional.of(achievement));
        when(achievementService.hasAchievement(1L, 1L)).thenReturn(true);
        expertAchievementHandler.handle(event);
        verify(achievementService, never()).createProgressIfNecessary(anyLong(), anyLong());
        verify(achievementService, never()).getProgress(anyLong(), anyLong());
        verify(achievementService, never()).giveAchievement(anyLong(), any());
    }

    @Test
    void testHandleWhenAchievementNotFound() {
        when(achievementCache.get("EXPERT")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> expertAchievementHandler.handle(event));
    }

}
