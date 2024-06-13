package faang.school.achievement.service.userAchievement;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserAchievementServiceImplTest {
    private static final long USER_ID = 1L;
    private static final long ACHIEVEMENT_ID = 1L;

    @Mock
    private UserAchievementRepository userAchievementRepository;
    @InjectMocks
    private UserAchievementServiceImpl userAchievementService;
    private UserAchievement userAchievement;

    @BeforeEach
    void setUp() {
        userAchievement = new UserAchievement();
        Achievement achievement = new Achievement();
        achievement.setId(ACHIEVEMENT_ID);
        userAchievement.setUserId(USER_ID);
        userAchievement.setAchievement(achievement);
    }

    @Test
    public void whenHasAchievementThenReturnFalse() {
        assertThat(userAchievementService.hasAchievement(USER_ID, ACHIEVEMENT_ID)).isFalse();
    }

    @Test
    public void whenHasAchievementThenReturnTrue() {
        when(userAchievementRepository.existsByUserIdAndAchievementId(USER_ID, ACHIEVEMENT_ID)).thenReturn(true);
        assertThat(userAchievementService.hasAchievement(USER_ID, ACHIEVEMENT_ID)).isTrue();
    }

    @Test
    public void whenAssignAchievementSuccessfully() {
        when(userAchievementRepository.existsByUserIdAndAchievementId(USER_ID, ACHIEVEMENT_ID)).thenReturn(true);
        userAchievementService.assignAchievement(userAchievement);
        verify(userAchievementRepository).save(userAchievement);
    }
}