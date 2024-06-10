package faang.school.achievement.handlers;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.SkillAcquiredEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WhoeverAchievementHandlerTest {

    private static final long ACTOR_ID = 1L;
    private static final long RECEIVER_ID = 2L;
    private static final long SKILL_ID = 3L;
    private static final String ACHIEVE_NAME = "SKILLER";
    private static final long ACHIEVEMENT_ID = 9L;
    private static final long ACHIEVE_POINTS = 10;


    @Mock
    private AchievementCache achievementCache;

    @Mock
    private AchievementService achievementService;

    @InjectMocks
    private WhoeverAchievementHandler whoeverAchievementHandler;

    private SkillAcquiredEvent messageEvent;
    private Achievement achievement;
    private AchievementProgress achievementProgress;

    @BeforeEach
    public void init() {
        messageEvent = SkillAcquiredEvent.builder()
                .actorId(ACTOR_ID)
                .receiverId(RECEIVER_ID)
                .skillId(SKILL_ID)
                .build();
        achievement = Achievement.builder()
                .id(ACHIEVEMENT_ID)
                .title(ACHIEVE_NAME)
                .points(ACHIEVE_POINTS)
                .build();
        achievementProgress = AchievementProgress.builder()
                .achievement(achievement)
                .userId(ACTOR_ID)
                .build();

        when(achievementCache.get(ACHIEVE_NAME)).thenReturn(achievement);
        when(achievementService.hasAchievement(ACTOR_ID, ACHIEVEMENT_ID)).thenReturn(false);
        lenient().when(achievementService.getProgress(ACTOR_ID, ACHIEVEMENT_ID)).thenReturn(achievementProgress);
    }

    @Test
    public void whenEventHandleButAchieveExistsAlready() {
        when(achievementService.hasAchievement(ACTOR_ID, ACHIEVEMENT_ID)).thenReturn(true);

        whoeverAchievementHandler.handleAchievement(messageEvent);

        verify(achievementService, never()).createProgressIfNecessary(ACTOR_ID, ACHIEVEMENT_ID);
    }

    @Test
    public void whenEventHandleAndGiveAchieve() {
        achievementProgress.setCurrentPoints(ACHIEVE_POINTS - 1);

        whoeverAchievementHandler.handleAchievement(messageEvent);

        verify(achievementService).createProgressIfNecessary(ACTOR_ID, ACHIEVEMENT_ID);
        verify(achievementService).updateProgress(achievementProgress);
        verify(achievementService).giveAchievement(ACTOR_ID, achievement);
    }

    @Test
    public void whenEventHandleButNotAchieve() {
        whoeverAchievementHandler.handleAchievement(messageEvent);

        verify(achievementService).createProgressIfNecessary(ACTOR_ID, ACHIEVEMENT_ID);
        verify(achievementService).updateProgress(achievementProgress);
    }
}