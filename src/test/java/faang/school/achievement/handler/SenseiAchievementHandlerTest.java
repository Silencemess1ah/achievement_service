package faang.school.achievement.handler;

import faang.school.achievement.config.cache.AchievementCache;
import faang.school.achievement.dto.event.MentorshipStartEvent;
import faang.school.achievement.handler.mentorship.SenseiAchievementHandler;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.service.achievement.AchievementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SenseiAchievementHandlerTest {

    @InjectMocks
    private SenseiAchievementHandler senseiAchievementHandler;

    @Mock
    private AchievementCache achievementCache;

    @Mock
    private AchievementService achievementService;

    private static final String ACHIEVEMENT_NAME = "SENSEI";
    private static final long ID = 1L;
    private static final int ACHIEVEMENT_POINTS = 10;
    private static final long CURRENT_POINTS = 9L;
    private Achievement achievement;
    private AchievementProgress achievementProgress;
    private MentorshipStartEvent mentorshipStartEvent;

    @BeforeEach
    public void init() {
        senseiAchievementHandler = new SenseiAchievementHandler(achievementCache, achievementService);
        ReflectionTestUtils.setField(senseiAchievementHandler, "achievementName", ACHIEVEMENT_NAME);
        ReflectionTestUtils.setField(senseiAchievementHandler, "achievementPoints", ACHIEVEMENT_POINTS);

        achievement = Achievement.builder()
                .id(ID)
                .title(ACHIEVEMENT_NAME)
                .build();

        achievementProgress = AchievementProgress.builder()
                .id(ID)
                .achievement(achievement)
                .currentPoints(CURRENT_POINTS)
                .build();

        mentorshipStartEvent = MentorshipStartEvent.builder()
                .mentorId(ID)
                .menteeId(ID)
                .build();
    }

    @Test
    @DisplayName("Успешное получение достижения SENSEI")
    public void whenHandleEventThenGiveAchievement() {
        when(achievementCache.getAchievement(ACHIEVEMENT_NAME)).thenReturn(achievement);
        when(achievementService.hasAchievement(ID, achievement.getId())).thenReturn(false);
        doNothing().when(achievementService).createProgressIfNecessary(ID, achievement.getId());
        when(achievementService.getProgress(ID, achievement.getId())).thenReturn(achievementProgress);
        doNothing().when(achievementService).giveAchievement(any(UserAchievement.class));

        senseiAchievementHandler.handleEvent(mentorshipStartEvent);

        verify(achievementCache).getAchievement(ACHIEVEMENT_NAME);
        verify(achievementService).hasAchievement(ID, achievement.getId());
        verify(achievementService).createProgressIfNecessary(ID, achievement.getId());
        verify(achievementService).getProgress(ID, achievement.getId());
        verify(achievementService).giveAchievement(any(UserAchievement.class));
    }

    @Test
    @DisplayName("Успешное увеличение прогресса без получения достижения SENSEI")
    public void whenHandleEventWithSmallProgressThenIncreaseProgress() {
        achievementProgress.setCurrentPoints(CURRENT_POINTS - 1);
        when(achievementCache.getAchievement(ACHIEVEMENT_NAME)).thenReturn(achievement);
        when(achievementService.hasAchievement(ID, achievement.getId())).thenReturn(false);
        doNothing().when(achievementService).createProgressIfNecessary(ID, achievement.getId());
        when(achievementService.getProgress(ID, achievement.getId())).thenReturn(achievementProgress);

        senseiAchievementHandler.handleEvent(mentorshipStartEvent);

        assertEquals(CURRENT_POINTS, achievementProgress.getCurrentPoints());
        verify(achievementCache).getAchievement(ACHIEVEMENT_NAME);
        verify(achievementService).hasAchievement(ID, achievement.getId());
        verify(achievementService).createProgressIfNecessary(ID, achievement.getId());
        verify(achievementService).getProgress(ID, achievement.getId());
    }

    @Test
    @DisplayName("Успешное завершение метода если у юзера уже есть достижение SENSEI")
    public void whenHandleEventWithUserHasAchievementThenSuccess() {
        when(achievementCache.getAchievement(ACHIEVEMENT_NAME)).thenReturn(achievement);
        when(achievementService.hasAchievement(ID, achievement.getId())).thenReturn(true);

        senseiAchievementHandler.handleEvent(mentorshipStartEvent);

        verify(achievementCache).getAchievement(ACHIEVEMENT_NAME);
        verify(achievementService).hasAchievement(ID, achievement.getId());
    }
}