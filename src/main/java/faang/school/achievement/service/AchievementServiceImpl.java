package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {
    private final UserAchievementRepository userAchievementRepository;


    @Override
    public boolean hasAchievement(long userId, long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Override
    public AchievementProgress getProgress(long userId, long achievementId) {
        return null;
    }

    @Override
    public void createProgressIfNecessary(long userId, long achievementId) {

    }

    @Override
    public void giveAchievement(long userId, Achievement achievement) {

    }

    @Override
    public boolean hasAchievement() {
        return userAchievementRepository.existsByUserIdAndAchievementId(userAchievement.getId(), userAchievement.getAchievement().getId());
    }
}
