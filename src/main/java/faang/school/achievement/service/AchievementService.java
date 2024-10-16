package faang.school.achievement.service;

import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.dto.AchievementDto;
import faang.school.achievement.model.entity.Achievement;
import faang.school.achievement.model.entity.UserAchievement;
import faang.school.achievement.model.event.AchievementEvent;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.messaging.achievement.AchievementEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final UserAchievementRepository userAchievementRepository;
    private final AchievementRepository achievementRepository;
    private final AchievementEventPublisher achievementEventPublisher;
    private final AchievementMapper achievementMapper;

    @Transactional
    public void assignAchievement(long userId, long achievementId) {
        if (userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId)) {
            throw new IllegalArgumentException("User already has this achievement");
        }

        if (!achievementRepository.existsById(achievementId)) {
            throw new IllegalArgumentException("Achievement with this id " + achievementId + " does not exist");
        }

        Achievement achievement = achievementRepository.getReferenceById(achievementId);

        UserAchievement userAchievement = UserAchievement.builder()
                .userId(userId)
                .achievement(achievement)
                .build();

        userAchievementRepository.save(userAchievement);
        achievementEventPublisher.publish(new AchievementEvent(achievementId, userId));
    }

    public AchievementDto getAchievement(long achievementId) {
        if (!achievementRepository.existsById(achievementId)) {
            throw new IllegalArgumentException("Achievement with this id " + achievementId + " does not exist");
        }
        Achievement achievement = achievementRepository.getReferenceById(achievementId);
        return achievementMapper.entityToDto(achievement);
    }
}
