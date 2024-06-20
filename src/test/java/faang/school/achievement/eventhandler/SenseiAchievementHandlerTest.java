package faang.school.achievement.eventhandler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.MentorshipStartEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SenseiAchievementHandlerTest {

    @InjectMocks
    private SenseiAchievementHandler senseiAchievementHandler;

    @Mock
    private AchievementCache achievementCache;

    @Mock
    private AchievementService achievementService;

    @Test
    void handleAchievement() {
        MentorshipStartEvent event = MentorshipStartEvent.builder()
                .mentorId(10L)
                .build();

        Achievement achievement = Achievement.builder()
                .id(1L)
                .title("SENSEI")
                .build();

        when(achievementCache.getAchievement(null)).thenReturn(achievement);

        long userId = event.getAchievementHolderId();
        long achievementId = achievement.getId();

        when(achievementService.hasAchievement(userId, achievementId)).thenReturn(false);
        AchievementProgress progress = AchievementProgress.builder()
                .id(3L)
                .achievement(achievement)
                .userId(userId)
                .currentPoints(29)
                .build();

        when(achievementService.getProgress(userId, achievementId)).thenReturn(progress);

        senseiAchievementHandler.handleEvent(event);

        verify(achievementService, times(1)).giveAchievement(achievement, userId);
    }
}
