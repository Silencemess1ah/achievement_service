package faang.school.achievement.service;

import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AchievementProgressServiceTest {

    @Mock
    private AchievementProgressRepository achievementProgressRepository;

    @InjectMocks
    private AchievementProgressService achievementProgressService;

    private Long userId;
    private Long achievementId;
    private AchievementProgress achievementProgress;

    @BeforeEach
    void setUp(){
        userId = 1L;
        achievementId = 2L;
        achievementProgress = new AchievementProgress();
    }

    @Test
    void createProgressIfNecessaryShouldCallRepositoryWithCorrectArguments(){
        achievementProgressService.createProgressIfNecessary(userId, achievementId);
        verify(achievementProgressRepository).createProgressIfNecessary(userId, achievementId);
    }

    @Test
    void getProgressShouldReturnAchievementProgressIfExists(){
        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId))
                .thenReturn(Optional.of(achievementProgress));
        AchievementProgress result = achievementProgressService.getProgress(userId, achievementId);
        assertEquals(achievementProgress, result);
    }

    @Test
    void getProgressShouldThrowEntityNotFoundExceptionIfNotFound(){
        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId))
                .thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> achievementProgressService.getProgress(userId, achievementId));
    }

    @Test
    void saveProgressShouldCallRepositoryWithCorrectArgument(){
        achievementProgressService.saveProgress(achievementProgress);
        verify(achievementProgressRepository).save(achievementProgress);
    }

    @Test
    void incrementProgressShouldUpdateAndSaveAchievementProgress(){
        achievementProgress.setCurrentPoints(10L);
        when(achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId))
                .thenReturn(Optional.of(achievementProgress));
        achievementProgressService.incrementProgress(userId, achievementId);
        assertEquals(11L, achievementProgress.getCurrentPoints());
        verify(achievementProgressRepository).save(achievementProgress);
    }
}