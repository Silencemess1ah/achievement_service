package faang.school.achievement.handler;

import faang.school.achievement.dto.achievement.AchievementDto;
import faang.school.achievement.event.InviteSentEvent;
import faang.school.achievement.service.achievement.AchievementService;
import faang.school.achievement.service.achievement_progress.AchievementProgressService;
import faang.school.achievement.service.user_achievement.UserAchievementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrganizerAchievementHandlerTest {

    @Mock
    private AchievementService achievementService;
    @Mock
    private AchievementProgressService achievementProgressService;
    @Mock
    private UserAchievementService userAchievementService;

    @InjectMocks
    private OrganizerAchievementHandler organizerAchievementHandler;

    private final long userId = 1L;
    private final long achievementId = 4L;
    private InviteSentEvent inviteSentEvent;
    private AchievementDto achievementDto;

    @BeforeEach
    void setUp() {
        inviteSentEvent = InviteSentEvent.builder()
                .userId(userId)
                .projectId(2L)
                .receiverId(3L)
                .build();

        achievementDto = AchievementDto.builder()
                .id(achievementId)
                .title("title")
                .points(100L)
                .build();
    }

    @Test
    void handle() {
        when(achievementService.getAchievementByTitle(any())).thenReturn(achievementDto);
        when(userAchievementService.hasAchievement(userId, achievementId)).thenReturn(false);
        when(achievementProgressService.incrementAndGetProgress(userId, achievementId)).thenReturn(100L);

        organizerAchievementHandler.handle(inviteSentEvent);

        InOrder inOrder = inOrder(achievementService, achievementProgressService, userAchievementService);
        inOrder.verify(achievementService).getAchievementByTitle(any());
        inOrder.verify(userAchievementService).hasAchievement(userId, achievementId);
        inOrder.verify(achievementProgressService).createProgressIfNecessary(userId, achievementId);
        inOrder.verify(userAchievementService).giveAchievement(userId, achievementDto);
    }
}