package faang.school.achievement.service;


import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;

public interface AchievementService {
    AchievementProgress getProgress(Long userId, Long achievementId);
    void giveAchievement(Long userId, Achievement achievement);
}

