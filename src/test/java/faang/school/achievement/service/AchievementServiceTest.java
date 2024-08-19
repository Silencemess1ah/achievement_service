package faang.school.achievement.service;

import faang.school.achievement.exception.DataNotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
public class AchievementServiceTest {

    public static final long USER_ID = 1L;
    public static final long ACHIEVEMENT_ID = 1L;


    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @InjectMocks
    private AchievementService achievementService;

    @Test
    public void test_has_achievement() {

        when(userAchievementRepository.existsByUserIdAndAchievementId(USER_ID, ACHIEVEMENT_ID)).thenReturn(true);

        Assertions.assertTrue(achievementService.hasAchievement(USER_ID, ACHIEVEMENT_ID));
    }

    @Test
    public void test_has_achievement_false() {

        when(userAchievementRepository.existsByUserIdAndAchievementId(USER_ID, ACHIEVEMENT_ID)).thenReturn(false);

        assertFalse(achievementService.hasAchievement(USER_ID, ACHIEVEMENT_ID));
    }

    @Test
    public void test_create_progress_if_necessary() {
        achievementService.createProgressIfNecessary(USER_ID, ACHIEVEMENT_ID);
        Mockito.verify(achievementProgressRepository, Mockito.times(1)).createProgressIfNecessary(USER_ID, ACHIEVEMENT_ID);
    }

    @Test
    public void test_get_progress() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(USER_ID, ACHIEVEMENT_ID)).thenReturn(Optional.of(new AchievementProgress()));
        assertNotNull(achievementService.getProgress(USER_ID, ACHIEVEMENT_ID));
    }

    @Test
    public void test_get_progress_not_found() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(USER_ID, ACHIEVEMENT_ID)).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> achievementService.getProgress(USER_ID, ACHIEVEMENT_ID));
    }

    @Test
    public void test_get_achievement() {
        achievementService.giveAchievement(new Achievement(), USER_ID);
        verify(userAchievementRepository, times(1)).save(Mockito.any());
    }

    @Test
    public void test_increment_achievement_progress() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(USER_ID, ACHIEVEMENT_ID)).thenReturn(Optional.of(new AchievementProgress()));
        achievementService.incrementAchievementProgress(USER_ID, ACHIEVEMENT_ID);
        verify(achievementProgressRepository, times(1)).save(Mockito.any());
    }

    @Test
    public void test_increment_achievement_progress_not_found() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(USER_ID, ACHIEVEMENT_ID)).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> achievementService.incrementAchievementProgress(USER_ID, ACHIEVEMENT_ID));
    }
}
