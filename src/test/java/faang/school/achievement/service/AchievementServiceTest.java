package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {
    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @InjectMocks
    private AchievementService achievementService;
    private long userId = 1L;
    private long achievementId = 2L;

    @Test
    void testHasAchievement() {
        when(userAchievementRepository.existsByUserIdAndAchievementId(anyLong(), anyLong()))
                .thenReturn(false);

        achievementService.hasAchievement(userId, achievementId);

        verify(userAchievementRepository, times(1))
                .existsByUserIdAndAchievementId(anyLong(), anyLong());
    }

    @Test
    void testGetProgress() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(anyLong(), anyLong()))
                .thenReturn(Optional.of(new AchievementProgress()));

        achievementService.getProgress(userId, achievementId);

        verify(achievementProgressRepository, times(1))
                .findByUserIdAndAchievementId(anyLong(), anyLong());
    }

    @Test
    void testCreateProgressIfNecessary() {
        doNothing().when(achievementProgressRepository).createProgressIfNecessary(anyLong(), anyLong());

        achievementService.createProgressIfNecessary(userId, achievementId);

        verify(achievementProgressRepository, times(1))
                .createProgressIfNecessary(anyLong(), anyLong());
    }

    @Test
    void testGiveAchievement() {
        when(userAchievementRepository.save(any(UserAchievement.class)))
                .thenReturn(new UserAchievement());

        achievementService.giveAchievement(userId, new Achievement());

        verify(userAchievementRepository, times(1))
                .save(any(UserAchievement.class));
    }
}