package faang.school.achievement.service;

import faang.school.achievement.config.context.UserContext;
import faang.school.achievement.dto.AchievementEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.publisher.achievement.AchievementPublisher;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {

    @Mock
    AchievementRepository achievementRepository;

    @Mock
    UserAchievementRepository userAchievementRepository;

    @Mock
    AchievementPublisher achievementPublisher;

    @Mock
    UserContext userContext;

    @InjectMocks
    AchievementService achievementService;

    long achievementId;
    long userId;
    Achievement achievement;

    @BeforeEach
    void setUp() {
        achievementId = 1;
        userId = 1;
        achievement = new Achievement();
    }

    @Test
    void grantAchievement() {
        when(userContext.getUserId()).thenReturn(userId);
        when(achievementRepository.findById(userId)).thenReturn(Optional.of(achievement));

        achievementService.grantAchievement(achievementId);

        verify(achievementRepository).findById(userId);
        verify(achievementPublisher).publish(any(AchievementEventDto.class));
        verify(userAchievementRepository).save(any(UserAchievement.class));
    }
}