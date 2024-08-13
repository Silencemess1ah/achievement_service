package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AchievementCache {
    private final Map<String, Achievement> cachedAchievements = new HashMap<>();
    private final AchievementRepository achievementRepository;

    public AchievementCache(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    public Achievement getAchievement(String title) {
        return cachedAchievements.get(title);
    }

    @PostConstruct
    private void cacheAchievements() {
        List<Achievement> achievements = achievementRepository.findAll();
        achievements.forEach(achievement -> cachedAchievements.put(achievement.getTitle(), achievement));
    }
}
