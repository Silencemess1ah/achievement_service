package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AchievementServiceTest {

    @InjectMocks
    private AchievementService achievementService;

    @Mock
    private UserAchievementRepository userAchievementRepository;

    @Mock
    private AchievementProgressRepository achievementProgressRepository;

    @Test
    void testGetProgressThrowException() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(anyLong(), anyLong()))
                .thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class,
                () -> achievementService.getProgress(1L, 2L));
    }

    @Test
    void testGetProgress() {
        AchievementProgress achievementProgress = new AchievementProgress();
        when(achievementProgressRepository.findByUserIdAndAchievementId(anyLong(), anyLong()))
                .thenReturn(Optional.of(achievementProgress));

        assertEquals(achievementProgress, achievementService.getProgress(1L, 2L));
    }

    @Test
    void giveAchievement() {
        Achievement achievement = new Achievement();
        long userId = 1L;
        UserAchievement userAchievement = UserAchievement.builder()
                .achievement(achievement)
                .userId(userId)
                .build();

        achievementService.giveAchievement(achievement, userId);
        verify(userAchievementRepository, times(1)).save(userAchievement);
    }
}
