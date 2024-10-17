package faang.school.achievement.service;

import faang.school.achievement.model.entity.Achievement;
import faang.school.achievement.model.entity.AchievementProgress;
import faang.school.achievement.model.entity.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.impl.AchievementServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AchievementServiceImplTest {

    @Mock
    private UserAchievementRepository userAchievementRepository;

    @Mock
    private AchievementProgressRepository progressRepository;

    @Mock
    private AchievementRepository achievementRepository;

    @InjectMocks
    private AchievementServiceImpl achievementService;

    @Captor
    private ArgumentCaptor<UserAchievement> userAchievementCaptor;

    private AchievementProgress achievementProgress;
    private Achievement achievement;

    @BeforeEach
    void setup(){
        achievement = Achievement.builder()
                .id(9L).title("LEADER")
                .points(20)
                .build();

        achievementProgress = AchievementProgress.builder()
                .id(1L)
                .currentPoints(0)
                .userId(1L)
                .achievement(achievement)
                .build();
    }

    @Test
    void hasAchievementTrue(){
        when(achievementService.hasAchievement(anyLong(), anyLong())).thenReturn(true);

        assertTrue(achievementService.hasAchievement(1L, 1L));
    }

    @Test
    void hasAchievementFalse(){
        when(achievementService.hasAchievement(anyLong(), anyLong())).thenReturn(false);

        assertFalse(achievementService.hasAchievement(1L, 1L));
    }

    @Test
    void testCreateProgressOk(){
        achievementService.createProgress(1L, 9L);

        verify(progressRepository).createProgressIfNecessary(anyLong(), anyLong());
    }

    @Test
    void testGetProgressOk(){
        when(progressRepository.findByUserIdAndAchievementId(anyLong(), anyLong()))
                .thenReturn(Optional.ofNullable(achievementProgress));

        assertEquals(achievementProgress, achievementService.getProgress(1L, 2L));
    }

    @Test
    void testGetProgressError(){
        assertThrows(EntityNotFoundException.class,
                () -> achievementService.getProgress(1L, 2L));
    }

    @Test
    void testGetAchievementError(){
        assertThrows(EntityNotFoundException.class,
                () -> achievementService.giveAchievement(1L, 2L));
    }

    @Test
    void testGetAchievementOk(){
        when(achievementRepository.findById(anyLong())).thenReturn(Optional.ofNullable(achievement));

        achievementService.giveAchievement(2L, 4L);

        verify(userAchievementRepository).save(userAchievementCaptor.capture());
        assertEquals(2L, userAchievementCaptor.getValue().getUserId());
        assertEquals(achievement, userAchievementCaptor.getValue().getAchievement());
    }

    @Test
    void saveProgress(){
        achievementService.saveProgress(achievementProgress);

        verify(progressRepository).save(achievementProgress);
    }
}
