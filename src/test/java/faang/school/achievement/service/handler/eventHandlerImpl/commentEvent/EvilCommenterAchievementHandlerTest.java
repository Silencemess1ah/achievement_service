package faang.school.achievement.service.handler.eventHandlerImpl.commentEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import faang.school.achievement.dto.event.CommentEvent;
import faang.school.achievement.exception.NotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.publisher.AchievementPublisher;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.handler.eventHandlerImpl.CommentEventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EvilCommenterAchievementHandlerTest {
    @Mock
    private AchievementService achievementService;
    @Mock
    private AchievementCache achievementCache;
    @Mock
    private AchievementPublisher achievementPublisher;
    @Captor
    private ArgumentCaptor<AchievementProgress> progressCaptor;
    private CommentEventHandler handler;
    private String nameAchievement = "EVIL_COMMENTER";

    private CommentEvent commentEvent;
    private Achievement achievement;
    private AchievementProgress achievementProgress;


    @BeforeEach
    void setUp() {
        prepareData();

        handler = new EvilCommenterAchievementHandler(achievementCache, achievementService, achievementPublisher,
                nameAchievement);
    }

    @Test
    void testProcessWithNonExistsAchievement() {
        // given
        doThrow(NotFoundException.class).when(achievementCache).get(Mockito.anyString());
        // then
        assertThrows(NotFoundException.class, () -> handler.process(commentEvent));
    }

    @Test
    void testProcessWithAlreadyGotAchievement() throws JsonProcessingException {
        // given
        boolean achievementWasReceived = true;
        when(achievementCache.get(nameAchievement)).thenReturn(achievement);
        when(achievementService.hasAchievement(commentEvent.getAuthorId(), achievement.getId())).thenReturn(achievementWasReceived);
        // when
        handler.process(commentEvent);
        // then
        verify(achievementService, times(0)).createProgressIfNecessary(commentEvent.getAuthorId(), achievement.getId());
    }

    @Test
    void testProcessWithAchievementProgressUpdate() throws JsonProcessingException {
        // given
        boolean achievementWasReceived = false;
        when(achievementCache.get(nameAchievement)).thenReturn(achievement);
        when(achievementService.hasAchievement(commentEvent.getAuthorId(), achievement.getId())).thenReturn(achievementWasReceived);
        when(achievementService.getProgress(commentEvent.getAuthorId(), achievement.getId())).thenReturn(achievementProgress);
        int progressPointExp = 1;
        // when
        handler.process(commentEvent);
        // then
        verify(achievementService, times(1)).saveProgress(progressCaptor.capture());
        assertEquals(progressPointExp, progressCaptor.getValue().getCurrentPoints());
    }

    @Test
    void testProcessWithReceivingAchievement() throws JsonProcessingException {
        // given
        boolean achievementWasReceived = false;
        when(achievementCache.get(nameAchievement)).thenReturn(achievement);
        when(achievementService.hasAchievement(commentEvent.getAuthorId(), achievement.getId())).thenReturn(achievementWasReceived);
        achievementProgress.setCurrentPoints(99);
        when(achievementService.getProgress(commentEvent.getAuthorId(), achievement.getId())).thenReturn(achievementProgress);
        // when
        handler.process(commentEvent);
        // then
        verify(achievementService, times(1)).deleteAchievementProgress(achievementProgress.getId());
        verify(achievementService, times(1)).giveAchievement(commentEvent.getAuthorId(), achievement);
        verify(achievementPublisher, times(1)).publish(Mockito.any());
    }

    private void prepareData() {
        commentEvent = CommentEvent.builder()
                .id(1L)
                .authorId(2L)
                .postId(3L)
                .content("content")
                .build();

        achievement = Achievement.builder()
                .id(9L)
                .title(nameAchievement)
                .points(100)
                .build();

        achievementProgress = AchievementProgress.builder()
                .id(4L)
                .userId(commentEvent.getAuthorId())
                .currentPoints(0)
                .achievement(achievement)
                .build();
    }
}