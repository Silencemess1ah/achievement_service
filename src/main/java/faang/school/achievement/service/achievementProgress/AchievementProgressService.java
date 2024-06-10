package faang.school.achievement.service.achievementProgress;

import faang.school.achievement.model.AchievementProgress;

public interface AchievementProgressService {
    void createProgressIfNecessary(long userId, long achievementId);
    AchievementProgress findByUserIdAndAchievementId(long userId, long achievementId);
    void updateAchievementProgressPoints(AchievementProgress achievementProgress);
}