package faang.school.achievement.handler.mentorship;

import static org.junit.jupiter.api.Assertions.*;

import faang.school.achievement.dto.achievement.AchievementDto;
import faang.school.achievement.event.mentorship.MentorshipStartEvent;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.achievement.AchievementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SenseiAchievementHandlerTest {

    @Mock
    private AchievementService achievementService;

    @Mock
    private AchievementMapper achievementMapper;

    @InjectMocks
    private SenseiAchievementHandler senseiAchievementHandler;

    private final long mentorId = 2L;
    private final long achievementId = 4L;

    @Value("${achievements.sensei.name}")
    private String ACHIEVEMENT;
    private MentorshipStartEvent mentorshipStartEvent;
    private AchievementProgress achievementProgress;
    private Achievement achievement;
    private AchievementDto achievementDto;

    @BeforeEach
    void setUp() {
        mentorshipStartEvent = MentorshipStartEvent.builder()
                .menteeId(1L)
                .mentorId(mentorId)
                .build();

        achievement = Achievement.builder()
                .id(achievementId)
                .title(ACHIEVEMENT)
                .points(20L)
                .build();

        achievementDto = AchievementDto.builder()
                .id(achievementId)
                .title(ACHIEVEMENT)
                .points(20L)
                .build();

        achievementProgress = AchievementProgress.builder()
                .id(5L)
                .userId(mentorId)
                .achievement(achievement)
                .currentPoints(19L)
                .build();
    }

    @Test
    void handle() {
        when(achievementService.getAchievementByTitle(ACHIEVEMENT)).thenReturn(achievementDto);
        when(achievementMapper.toEntity(achievementDto)).thenReturn(achievement);
        when(achievementService.hasAchievement(mentorId, achievementId)).thenReturn(false);
        when(achievementService.getProgress(mentorId, achievementId)).thenReturn(achievementProgress);

        senseiAchievementHandler.handle(mentorshipStartEvent);
        assertEquals(20L, achievementProgress.getCurrentPoints());

        InOrder inOrder = inOrder(achievementService);
        inOrder.verify(achievementService).getAchievementByTitle(ACHIEVEMENT);
        inOrder.verify(achievementService).hasAchievement(mentorId, achievementId);
        inOrder.verify(achievementService).createProgressIfNecessary(mentorId, achievementId);
        inOrder.verify(achievementService).getProgress(mentorId, achievementId);
        inOrder.verify(achievementService).giveAchievement(mentorId, achievement);
    }
}