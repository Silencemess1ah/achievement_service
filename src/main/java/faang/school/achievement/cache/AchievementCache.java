package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class AchievementCache {

    private final AchievementRepository achievementRepository;

    public Map<String, Achievement> achievementCache = new HashMap<>();

    public Achievement get(String title) {
        return achievementCache.get(title);
    }

    @PostConstruct
    public void fillAchievementCache() {
        List<Achievement> achievements = achievementRepository.findAll();
        achievements.forEach(achievement -> {
                String title = achievement.getTitle();
                achievementCache.put(title, achievement);
        });
    }
}
