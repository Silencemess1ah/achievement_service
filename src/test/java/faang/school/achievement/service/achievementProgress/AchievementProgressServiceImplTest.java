package faang.school.achievement.service.achievementProgress;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementProgressServiceImplTest {
    private static final long USER_ID = 1L;
    private static final long ACHIEVEMENT_ID = 1L;

    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @InjectMocks
    private AchievementProgressServiceImpl achievementProgressService;
    private AchievementProgress achievementProgress;

    @BeforeEach
    void setUp() {
        achievementProgress = new AchievementProgress();
        Achievement achievement = new Achievement();
        achievement.setId(ACHIEVEMENT_ID);
        achievementProgress.setUserId(USER_ID);
        achievementProgress.setAchievement(achievement);
    }

    @Test
    public void whenCreateProgressIfNecessary() {
        achievementProgressService.createProgressIfNecessary(USER_ID, ACHIEVEMENT_ID);
        verify(achievementProgressRepository).createProgressIfNecessary(USER_ID, ACHIEVEMENT_ID);
    }

    @Test
    public void whenFindByUserIdAndAchievementIdThenThrowsException() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(USER_ID, ACHIEVEMENT_ID)).thenReturn(Optional.empty());
        Assert.assertThrows(EntityNotFoundException.class,
                () -> achievementProgressService.findByUserIdAndAchievementId(USER_ID, ACHIEVEMENT_ID));
    }

    @Test
    public void whenFindByUserIdAndAchievementIdThenGetAchievement() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(USER_ID, ACHIEVEMENT_ID)).thenReturn(Optional.of(achievementProgress));
        AchievementProgress actual = achievementProgressService.findByUserIdAndAchievementId(USER_ID, ACHIEVEMENT_ID);
        assertThat(actual).isEqualTo(achievementProgress);
    }

    @Test
    public void whenUpdateAchievementProgressPointsThenIncrementPointsAndSaveProgress() {
        achievementProgress.setCurrentPoints(3L);
        achievementProgressService.updateAchievementProgressPoints(achievementProgress);
        verify(achievementProgressRepository).save(achievementProgress);
        assertThat(achievementProgress.getCurrentPoints()).isEqualTo(4L);
    }
}