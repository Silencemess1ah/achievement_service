package faang.school.achievement.service.handler.eventHandlerImpl.followerEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.event.FollowerEvent;
import faang.school.achievement.exception.NotFoundException;
import faang.school.achievement.mapper.AchievementMapperImpl;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.handler.eventHandler.followerEvent.BloggerHandler;
import faang.school.achievement.service.handler.eventHandler.followerEvent.FollowerEventHandler;
import faang.school.achievement.service.publisher.AchievementPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BloggerHandlerTest {
    @Mock
    private AchievementService achievementService;
    @Mock
    private AchievementCache achievementCache;
    @Mock
    private AchievementPublisher achievementPublisher;
    @Spy
    private AchievementMapperImpl mapper;
    @Captor
    private ArgumentCaptor<AchievementProgress> progressCaptor;
    private FollowerEventHandler handler;
    private final String achievementTitle = "BLOGGER";

    private Achievement achievement;
    private AchievementProgress achievementProgress;
    private AchievementProgressDto achievementProgressDto;
    private FollowerEvent event;

    @BeforeEach
    void setUp() {
        prepareData();
        handler = new BloggerHandler(achievementCache, achievementService, achievementPublisher,
                achievementTitle, mapper);
    }

    @Test
    void testProcessWithNonExistsAchievement() {
        // given
        doThrow(NotFoundException.class).when(achievementCache).get(Mockito.anyString());
        // then
        assertThrows(NotFoundException.class, () -> handler.process(event));
    }

    @Test
    void testProcessWithAlreadyGotAchievement() throws JsonProcessingException {
        // given
        boolean achievementWasReceived = true;
        when(achievementCache.get(achievementTitle)).thenReturn(achievement);
        when(achievementService.hasAchievement(event.getUserId(), achievement.getId())).thenReturn(achievementWasReceived);
        // when
        handler.process(event);
        // then
        verify(achievementService, times(0)).createProgressIfNecessary(event.getUserId(), achievement.getId());
    }

    @Test
    void testProcessWithAchievementProgressUpdate() throws JsonProcessingException {
        // given
        boolean achievementWasReceived = false;
        when(achievementCache.get(achievementTitle)).thenReturn(achievement);
        when(achievementService.hasAchievement(event.getUserId(), achievement.getId())).thenReturn(achievementWasReceived);
        when(achievementService.getProgress(event.getUserId(), achievement.getId())).thenReturn(achievementProgressDto);
        int progressPointExp = 1;
        // when
        handler.process(event);
        // then
        verify(achievementService, times(1)).saveProgress(progressCaptor.capture());
        assertEquals(progressPointExp, progressCaptor.getValue().getCurrentPoints());
    }

    @Test
    void testProcessWithReceivingAchievement() throws JsonProcessingException {
        // given
        boolean achievementWasReceived = false;
        when(achievementCache.get(achievementTitle)).thenReturn(achievement);
        when(achievementService.hasAchievement(event.getUserId(), achievement.getId())).thenReturn(achievementWasReceived);
        achievementProgressDto.setCurrentPoints(999);
        when(achievementService.getProgress(event.getUserId(), achievement.getId())).thenReturn(achievementProgressDto);
        // when
        handler.process(event);
        // then
        verify(achievementService, times(1)).deleteAchievementProgress(achievementProgress.getId());
        verify(achievementService, times(1)).giveAchievement(event.getUserId(), achievement);
        verify(achievementPublisher, times(1)).publish(Mockito.any());
    }

    private void prepareData() {
        event = FollowerEvent.builder()
                .userId(1L)
                .followerId(2L)
                .build();

        achievement = Achievement.builder()
                .id(10L)
                .title(achievementTitle)
                .points(1000)
                .build();

        achievementProgress = AchievementProgress.builder()
                .id(3L)
                .userId(event.getUserId())
                .currentPoints(0)
                .achievement(achievement)
                .build();

        achievementProgressDto = mapper.toAchievementProgressDto(achievementProgress);
    }
}