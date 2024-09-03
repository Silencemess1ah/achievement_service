package faang.school.achievement.service;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementCache achievementCache;

    public Achievement getAchievementFromCache(String achievementTitle) {
        return achievementCache.getAchievement(achievementTitle);
    }
}