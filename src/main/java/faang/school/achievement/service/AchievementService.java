package faang.school.achievement.service;

import faang.school.achievement.config.context.UserContext;
import faang.school.achievement.dto.AchievementEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.publisher.achievement.AchievementPublisher;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementPublisher achievementPublisher;
    private final UserContext userContext;

    @Transactional
    public void grantAchievement(long achievementId) {
        long userId = userContext.getUserId();
        Achievement achievement = achievementRepository.getById(userId);

        AchievementEventDto event = new AchievementEventDto(userId, achievementId);
        achievementPublisher.publish(event);

        UserAchievement userAchievement = UserAchievement.builder()
            .userId(userId)
            .achievement(achievement)
            .build();
        userAchievementRepository.save(userAchievement);
    }
}
