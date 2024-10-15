package faang.school.achievement.service;

import faang.school.achievement.model.AchievementProgress;

public interface AchievementService {

    boolean hasAchievement(Long userId, Long achievementId);

    void createProgressIfNeccessary(Long userId, Long achievementId);

    AchievementProgress getProgress(Long userId, Long achievementId);

    void giveAchievement(Long userId, Long achievementId);
}
