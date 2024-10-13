package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.model.Achievement;

public interface AchievementService {
    boolean hasAchievement(long userId, long achievementId);

    void createProgressIfNecessary(long userId, long achievementId);

    AchievementProgressDto getProgress(long userId, long achievementId);

    void giveAchievement(long userId, Achievement achievement);
}
