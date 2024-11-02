package faang.school.achievement.service;

import faang.school.achievement.model.dto.AchievementRedisDto;

public interface AchievementCache {

    void addAchievementCache();

    AchievementRedisDto getAchievementCache(String title);
}
