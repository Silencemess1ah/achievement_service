package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class AchievementCache {
    private final Map<String, Achievement> achievements = new HashMap<>();
    private final AchievementRepository achievementRepository;

    public AchievementCache(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    @PostConstruct
    public void init() {
        achievementRepository.findAll().forEach(achievement -> achievements.put(achievement.getTitle(), achievement));
    }

    public Optional<Achievement> get(String achievementTitle) {
        return Optional.ofNullable(achievements.get(achievementTitle));
    }
}