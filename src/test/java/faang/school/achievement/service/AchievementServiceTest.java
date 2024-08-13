package faang.school.achievement.service;

import faang.school.achievement.config.context.UserContext;
import faang.school.achievement.exception.DataNotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AchievementServiceTest {

    @Mock
    private AchievementRepository achievementRepository;

    @Mock
    private UserAchievementRepository userAchievementRepository;

    @Mock
    private UserContext userContext;

    @InjectMocks
    private AchievementService achievementService;

    @Test
    @DisplayName("Test save achievement to user - achievement found")
    public void testSaveAchievementToUserAchievementFound() {
        long achievementId = 1L;
        long userId = 1L;
        Achievement achievement = new Achievement();
        when(achievementRepository.findById(achievementId)).thenReturn(Optional.of(achievement));
        when(userContext.getUserId()).thenReturn(userId);

        achievementService.saveAchievementToUser(achievementId);

        verify(userAchievementRepository).save(any(UserAchievement.class));
    }

    @Test
    @DisplayName("Test save achievement to user - achievement not found")
    public void testSaveAchievementToUser_AchievementNotFound() {
        long achievementId = 1L;
        when(achievementRepository.findById(achievementId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> achievementService.saveAchievementToUser(achievementId));
    }
}
