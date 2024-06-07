package faang.school.achievement.service;

import faang.school.achievement.exception.NotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementServiceImplTest {

    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @Mock
    private AchievementRepository achievementRepository;

    @InjectMocks
    private AchievementServiceImpl achievementService;

    private final long userId = 1L;
    private final long achievementId = 2L;
    private final String ACHIEVEMENT = "COLLECTOR";
    private AchievementProgress achievementProgress;
    private Achievement achievement;

    @BeforeEach
    void setUp() {
        achievement = Achievement.builder()
                .id(achievementId)
                .title(ACHIEVEMENT)
                .points(100L)
                .build();

        achievementProgress = AchievementProgress.builder()
                .id(5L)
                .userId(userId)
                .achievement(achievement)
                .currentPoints(99L)
                .build();
    }

    @Test
    void hasAchievement() {
        when(userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId)).thenReturn(true);

        boolean actual = achievementService.hasAchievement(userId, achievementId);
        assertTrue(actual);

        InOrder inOrder = inOrder(userAchievementRepository, achievementProgressRepository, achievementRepository);
        inOrder.verify(userAchievementRepository).existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Test
    void createProgressIfNecessary() {
        achievementService.createProgressIfNecessary(userId, achievementId);

        InOrder inOrder = inOrder(userAchievementRepository, achievementProgressRepository, achievementRepository);
        inOrder.verify(achievementProgressRepository).createProgressIfNecessary(userId, achievementId);
    }

    @Test
    void getProgress() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)).thenReturn(Optional.of(achievementProgress));

        AchievementProgress actual = achievementService.getProgress(userId, achievementId);
        assertEquals(achievementProgress, actual);

        InOrder inOrder = inOrder(userAchievementRepository, achievementProgressRepository, achievementRepository);
        inOrder.verify(achievementProgressRepository).findByUserIdAndAchievementId(userId, achievementId);
    }

    @Test
    void getProgressNotFound() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)).thenReturn(Optional.empty());

        NotFoundException e = assertThrows(NotFoundException.class, () -> achievementService.getProgress(userId, achievementId));
        assertEquals("Achievement progress with userId=" + userId + " and achievementId=" + achievementId + " not found", e.getMessage());
    }

    @Test
    void giveAchievement() {
        achievementService.giveAchievement(userId, achievement);

        InOrder inOrder = inOrder(userAchievementRepository, achievementProgressRepository, achievementRepository);
        inOrder.verify(userAchievementRepository).save(any(UserAchievement.class));
    }

    @Test
    void getAchievementByTitle() {
        when(achievementRepository.findByTitle(ACHIEVEMENT)).thenReturn(Optional.of(achievement));

        Achievement actual = achievementService.getAchievementByTitle(ACHIEVEMENT);
        assertEquals(achievement, actual);

        InOrder inOrder = inOrder(userAchievementRepository, achievementProgressRepository, achievementRepository);
        inOrder.verify(achievementRepository).findByTitle(ACHIEVEMENT);
    }

    @Test
    void getAchievementByTitleNotFound() {
        when(achievementRepository.findByTitle(ACHIEVEMENT)).thenReturn(Optional.empty());


        NotFoundException e = assertThrows(NotFoundException.class, () -> achievementService.getAchievementByTitle(ACHIEVEMENT));
        assertEquals("Achievement with title=" + ACHIEVEMENT + " not found", e.getMessage());
    }
}