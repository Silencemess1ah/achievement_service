package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.validator.AchievementCacheValidator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class AchievementCache {
    private final AchievementRepository achievementRepository;
    private final AchievementCacheValidator achievementCacheValidator;
    private Map<String, Achievement> achievementCache;

    @PostConstruct
    public void init() {
        achievementCache = new ConcurrentHashMap<>();
        achievementRepository.findAll().forEach(achievement ->
                achievementCache.put(achievement.getTitle(), achievement));
    }

    public Achievement get(String title) {
        Achievement achievement = achievementCache.get(title);
        achievementCacheValidator.validateAchievementNotNull(achievement, title);
        return achievement;
    }
}
