package faang.school.achievement.service;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AchievementUserServiceTest {

    @Mock
    private UserAchievementRepository userAchievementRepository;

    @Mock
    private AchievementCache achievementCache;

    @InjectMocks
    private AchievementUserService achievementUserService;

    private Long userId;
    private Long achievementId;
    private Achievement achievement;
    private UserAchievement userAchievement;

    @BeforeEach
    void setUp(){
        userId = 1L;
        achievementId = 2L;
        achievement = new Achievement();
        userAchievement = new UserAchievement();
    }

    @Test
    void hasAchievementShouldReturnTrueIfUserHasAchievement() {
        when(userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId))
                .thenReturn(true);
        boolean result = achievementUserService.hasAchievement(userId, achievementId);
        assertTrue(result);
    }

    @Test
    void hasAchievementShouldReturnFalseIfUserDoesNotHaveAchievement() {
        when(userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId))
                .thenReturn(false);
        boolean result = achievementUserService.hasAchievement(userId, achievementId);
        assertFalse(result);
    }

    @Test
    void giveAchievementShouldThrowNoSuchElementExceptionIfAchievementNotFound() {
        when(achievementCache.get(any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> achievementUserService.giveAchievement(userId, achievement));
    }

    @Test
    void getAchievementShouldReturnAchievementIfExists() {
        when(achievementCache.get(achievementId.toString())).thenReturn(Optional.of(achievement));
        Achievement result = achievementUserService.getAchievement(achievementId);
        assertEquals(achievement, result);
    }

    @Test
    void getAchievementShouldThrowNoSuchElementExceptionIfNotFound() {
        when(achievementCache.get(achievementId.toString())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> achievementUserService.getAchievement(achievementId));
    }
}