package faang.school.achievement.handler;

import faang.school.achievement.event.NiceGuyEvent;
import faang.school.achievement.model.dto.AchievementRedisDto;
import faang.school.achievement.model.entity.Achievement;
import faang.school.achievement.model.entity.AchievementProgress;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.impl.AchievementServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class NiceGuyAchievementHandlerTest {
    @Mock
    private AchievementCache achievementCache;

    @Mock
    private AchievementServiceImpl achievementService;

    @InjectMocks
    private NiceGuyAchievementHandler niceGuyAchievementHandler;

    @Captor
    private ArgumentCaptor<AchievementProgress> progressCaptor;

    private final String niceguyTitle = "NICEGUY";
    @Test
    void testHandleOk() {
        NiceGuyEvent niceGuyEvent = NiceGuyEvent.builder()
                .authorId(1L)
                .receiverId(2L)
                .recommendationId(1L)
                .build();
        Achievement achievement = Achievement.builder()
                .id(10L)
                .title(niceguyTitle)
                .points(10)
                .build();
        AchievementProgress progress = AchievementProgress.builder()
                .id(1L)
                .userId(1L)
                .achievement(achievement)
                .currentPoints(0)
                .build();
        AchievementRedisDto achievementRedisDto = new AchievementRedisDto();
        achievementRedisDto.setId(10L);
        when(achievementCache.getAchievementCache(anyString())).thenReturn(achievementRedisDto);
        when(achievementService.hasAchievement(1L, 10)).thenReturn(false);
        when(achievementService.getProgress(anyLong(), anyLong())).thenReturn(progress);

        niceGuyAchievementHandler = new NiceGuyAchievementHandler(achievementCache,achievementService,niceguyTitle);
        niceGuyAchievementHandler.handle(niceGuyEvent);

        verify(achievementService).createProgress(anyLong(),anyLong());
        verify(achievementService).getProgress(anyLong(), anyLong());
        verify(achievementService, never()).giveAchievement(anyLong(), anyLong());
        verify(achievementService).saveProgress(progressCaptor.capture());
        assertEquals(1, progressCaptor.getValue().getCurrentPoints());

    }
}
