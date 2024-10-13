package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementProgressDto;

public interface AchievementService {
    boolean hasAchievement(long userId, long achievementId);

    void createProgressIfNecessary(long userId, long achievementId);

    AchievementProgressDto getProgress(long userId, long achievementId);
}
