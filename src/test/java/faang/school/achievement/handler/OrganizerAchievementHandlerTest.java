package faang.school.achievement.handler;

import faang.school.achievement.event.InviteSentEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrganizerAchievementHandlerTest {

    @Mock
    private  AchievementService achievementService;

    @InjectMocks
    private OrganizerAchievementHandler organizerAchievementHandler;

    private final long userId = 1L;
    private final long achievementId = 4L;
    private final String ACHIEVEMENT = "COLLECTOR";
    private InviteSentEvent inviteSentEvent;
    private AchievementProgress achievementProgress;
    private Achievement achievement;

    @BeforeEach
    void setUp() {
        inviteSentEvent = InviteSentEvent.builder()
                .userId(userId)
                .projectId(2L)
                .receiverId(3L)
                .build();

        achievement = Achievement.builder()
                .id(achievementId)
                .title(ACHIEVEMENT)
                .points(100L)
                .build();

        achievementProgress = AchievementProgress.builder()
                .id(5L)
                .userId(userId)
                .achievement(achievement)
                .currentPoints(99L)
                .build();
    }

    @Test
    void handle() {
        when(achievementService.getAchievementByTitle(ACHIEVEMENT)).thenReturn(achievement);
        when(achievementService.hasAchievement(userId, achievementId)).thenReturn(false);
        when(achievementService.getProgress(userId, achievementId)).thenReturn(achievementProgress);

        organizerAchievementHandler.handle(inviteSentEvent);
        assertEquals(100L, achievementProgress.getCurrentPoints());

        InOrder inOrder = inOrder(achievementService);
        inOrder.verify(achievementService).getAchievementByTitle(ACHIEVEMENT);
        inOrder.verify(achievementService).hasAchievement(userId, achievementId);
        inOrder.verify(achievementService).createProgressIfNecessary(userId, achievementId);
        inOrder.verify(achievementService).getProgress(userId, achievementId);
        inOrder.verify(achievementService).giveAchievement(userId, achievement);
    }
}