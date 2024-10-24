package faang.school.achievement.handler;


import faang.school.achievement.model.dto.AchievementRedisDto;
import faang.school.achievement.model.entity.Achievement;
import faang.school.achievement.model.entity.AchievementProgress;
import faang.school.achievement.model.event.ManagerAchievementEvent;
import faang.school.achievement.model.event.PostEvent;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ManagerAchievementListenerTest {
    @Mock
    private AchievementCache achievementCache;

    @Mock
    private AchievementServiceImpl achievementService;

    @InjectMocks
    private ManagerAchievementHandler managerAchievementHandler;

    @Captor
    private ArgumentCaptor<AchievementProgress> progressCaptor;

    private final String managerTitle = "MANAGER";

    @Test
    void testIsOk() {
        ManagerAchievementEvent event = ManagerAchievementEvent.builder()
                .authorId(1L)
                .teamId(1L)
                .projectId(1L)
                .build();
        Achievement achievement = Achievement.builder()
                .id(5L)
                .title(managerTitle)
                .points(10)
                .build();

        AchievementProgress achievementProgress = AchievementProgress.builder()
                .id(1L)
                .achievement(achievement)
                .currentPoints(0)
                .build();
        AchievementRedisDto achievementRedisDto = new AchievementRedisDto();
        achievementRedisDto.setId(5);

        when(achievementCache.getAchievementCache(anyString())).thenReturn(achievementRedisDto);
        when(achievementService.hasAchievement(1L, 5)).thenReturn(false);
        when(achievementService.getProgress(anyLong(), anyLong())).thenReturn(achievementProgress);


        managerAchievementHandler = new ManagerAchievementHandler(achievementCache, achievementService, managerTitle);

        managerAchievementHandler.handle(event);

        verify(achievementService).createProgress(anyLong(), anyLong());
        verify(achievementService).getProgress(anyLong(), anyLong());
        verify(achievementService, never()).giveAchievement(anyLong(), anyLong());
        verify(achievementService).saveProgress(progressCaptor.capture());

        assertEquals(1, progressCaptor.getValue().getCurrentPoints());
    }
}
