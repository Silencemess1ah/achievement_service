package faang.school.achievement.service;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AchievementService {
    private final AchievementCache achievementCache;
    private final AchievementProgressService achievementProgressService;

    public Achievement getAchievementFromCache(String achievementTitle) {
        return achievementCache.getAchievement(achievementTitle);
    }

}
