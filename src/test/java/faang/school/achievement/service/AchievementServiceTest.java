package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.action.internal.EntityActionVetoException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AchievementServiceTest {
    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @Mock
    private AchievementRepository achievementRepository;
    @InjectMocks
    private AchievementService achievementService;

    private boolean respondFromDB;
    private long userId;
    private long achievementId;
    private Achievement achievement;
    private AchievementProgress progress;

    @BeforeEach
    void setUp() {
        userId = 1L;
        achievementId = 2L;
        achievement = Achievement.builder()
                .id(achievementId)
                .title("test")
                .description("test")
                .points(100)
                .build();
        progress = AchievementProgress.builder()
                .id(3L)
                .userId(userId)
                .currentPoints(99)
                .build();
    }

    @Test
    public void testHasAchievement() {
        Mockito.when(userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId)).thenReturn(true);
        Boolean respond = achievementService.hasAchievement(userId, achievementId);
        Mockito.verify(userAchievementRepository, Mockito.times(1)).existsByUserIdAndAchievementId(userId, achievementId);
        Assertions.assertEquals(true, respond);
    }

    @Test
    public void testHasNotAchievement() {
        Mockito.when(userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId)).thenReturn(false);
        Boolean respond = achievementService.hasAchievement(userId, achievementId);
        Mockito.verify(userAchievementRepository, Mockito.times(1)).existsByUserIdAndAchievementId(userId, achievementId);
        Assertions.assertEquals(false, respond);
    }

    @Test
    public void testCreateProgressIfNecessary() {
        Mockito.doNothing().when(achievementProgressRepository).createProgressIfNecessary(userId, achievementId);
        achievementService.createProgressIfNecessary(userId, achievementId);
        Mockito.verify(achievementProgressRepository, Mockito.times(1)).createProgressIfNecessary(userId, achievementId);
    }

    @Test
    public void testGetProgress() {
        Mockito.when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId))
                .thenReturn(Optional.of(progress));
        achievementService.getProgress(userId, achievementId);
        Mockito.verify(achievementProgressRepository, Mockito.times(1)).findByUserIdAndAchievementId(userId, achievementId);
    }

    @Test
    public void testGetNoProgress() {
        Mockito.when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId))
                .thenReturn(Optional.empty());
        Assert.assertThrows(EntityNotFoundException.class, ()-> achievementService.getProgress(userId, achievementId));
    }

    @Test
    public void updateProgressTest() {
        Mockito.when(achievementProgressRepository.save(progress)).thenAnswer(invocation -> invocation.getArgument(0));
        achievementService.updateProgress(progress);
        Mockito.verify(achievementProgressRepository, Mockito.times(1)).save(progress);
    }

    @Test
    public void giveAchievementTest() {
        Mockito.when(userAchievementRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));
        achievementService.giveAchievement(userId, achievement);
        Mockito.verify(userAchievementRepository, Mockito.times(1)).save(Mockito.any());
    }
}
