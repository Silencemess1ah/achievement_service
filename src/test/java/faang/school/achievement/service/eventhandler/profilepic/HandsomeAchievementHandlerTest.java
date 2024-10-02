package faang.school.achievement.service.eventhandler.profilepic;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.event.ProfilePicEvent;
import faang.school.achievement.service.AchievementService;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class HandsomeAchievementHandlerTest {

    private final static String achievementTitle = "HANDSOME";

    private final AchievementCache achievementCache = mock(AchievementCache.class);
    private final AchievementService achievementService = mock(AchievementService.class);

    private final HandsomeAchievementHandler handsomeAchievementHandler
            = new HandsomeAchievementHandler(achievementTitle, achievementCache, achievementService);

    @Test
    void testHandle_userHasAchievement_doNothing() {
        long achievementId = 1L;
        long userId = 11L;

        ProfilePicEvent event = new ProfilePicEvent();
        event.setUserId(userId);

        Achievement achievement = Achievement.builder()
                .id(achievementId)
                .build();

        when(achievementCache.get(achievementTitle)).thenReturn(achievement);
        when(achievementService.userHasAchievement(userId, achievementId)).thenReturn(true);

        handsomeAchievementHandler.handle(event);

        verify(achievementCache, times(1)).get(achievementTitle);
        verify(achievementService, times(1)).userHasAchievement(userId, achievementId);
        verifyNoMoreInteractions(achievementCache, achievementService);
    }

    @Test
    void testHandle_userHasNotEnoughPoints_saveProgressNotGiveAchievement() {
        long achievementId = 1L;
        long userId = 11L;

        ProfilePicEvent event = new ProfilePicEvent();
        event.setUserId(userId);

        Achievement achievement = Achievement.builder()
                .id(achievementId)
                .points(2L)
                .build();

        long initialPoints = 0L;
        AchievementProgress progress = AchievementProgress.builder()
                .currentPoints(initialPoints)
                .build();

        when(achievementCache.get(achievementTitle)).thenReturn(achievement);
        when(achievementService.userHasAchievement(userId, achievementId)).thenReturn(false);
        when(achievementService.getProgress(userId, achievementId)).thenReturn(progress);
        when(achievementService.incrementCurrentPointsForUser(progress)).thenAnswer(new Answer<AchievementProgress>() {
            @Override
            public AchievementProgress answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                AchievementProgress progress =(AchievementProgress) args[0];
                progress.setCurrentPoints(initialPoints+1);
                return progress;
            }
        });

        handsomeAchievementHandler.handle(event);

        verify(achievementCache, times(1)).get(achievementTitle);
        verify(achievementService, times(1)).userHasAchievement(userId, achievementId);
        verify(achievementService, times(1)).createProgressIfNecessary(userId, achievementId);
        verify(achievementService, times(1)).getProgress(userId, achievementId);
        verify(achievementService, times(1)).incrementCurrentPointsForUser(progress);
        assertEquals(initialPoints + 1, progress.getCurrentPoints());
        verifyNoMoreInteractions(achievementCache, achievementService);
    }

    @Test
    void testHandle_userHasEnoughPoints_GiveAchievement() {
        long achievementId = 1L;
        long userId = 11L;

        ProfilePicEvent event = new ProfilePicEvent();
        event.setUserId(userId);

        Achievement achievement = Achievement.builder()
                .id(achievementId)
                .points(1L)
                .build();

        long initialPoints = 0L;
        AchievementProgress progress = AchievementProgress.builder()
                .id(12L)
                .userId(userId)
                .currentPoints(initialPoints)
                .build();

        when(achievementCache.get(achievementTitle)).thenReturn(achievement);
        when(achievementService.userHasAchievement(userId, achievementId)).thenReturn(false);
        when(achievementService.getProgress(userId, achievementId)).thenReturn(progress);
        when(achievementService.incrementCurrentPointsForUser(progress)).thenAnswer(new Answer<AchievementProgress>() {
            @Override
            public AchievementProgress answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                AchievementProgress progress =(AchievementProgress) args[0];
                progress.setCurrentPoints(initialPoints+1);
                return progress;
            }
        });

        handsomeAchievementHandler.handle(event);

        verify(achievementCache, times(1)).get(achievementTitle);
        verify(achievementService, times(1)).userHasAchievement(userId, achievementId);
        verify(achievementService, times(1)).createProgressIfNecessary(userId, achievementId);
        verify(achievementService, times(1)).getProgress(userId, achievementId);
        assertEquals(initialPoints + 1, progress.getCurrentPoints());
        verify(achievementService, times(1)).giveAchievement(userId, achievement);
        verify(achievementService, times(1)).incrementCurrentPointsForUser(progress);
        verifyNoMoreInteractions(achievementCache, achievementService);
    }
}