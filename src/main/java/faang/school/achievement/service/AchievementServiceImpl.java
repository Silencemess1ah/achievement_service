package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;

    @Override
    public boolean hasAchievement(long userId, long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Override
    public AchievementProgress getProgress(long userId, long achievementId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId).orElseThrow(
                () -> new EntityNotFoundException("Achievement %d progress not found".formatted(achievementId)));
    }

    @Override
    public void createProgressIfNecessary(long userId, long achievementId) {
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
    }

    @Override
    public void giveAchievement(long userId, Achievement achievement) {
        UserAchievement userAchievement = UserAchievement.builder()
                .userId(userId)
                .achievement(achievement)
                .build();
        userAchievementRepository.save(userAchievement);
    }
}
