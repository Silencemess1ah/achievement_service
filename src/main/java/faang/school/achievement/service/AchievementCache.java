package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class AchievementCache {
    private final AchievementRepository achievementRepository;
    private Map<String, Achievement> achievementCache;

    @PostConstruct
    public void init() {
        achievementCache = new ConcurrentHashMap<>();
        achievementRepository.findAll().forEach(achievement ->
                achievementCache.put(achievement.getTitle(), achievement));
    }

    public Achievement get(String title) {
        Achievement achievement = achievementCache.get(title);
        if (achievement == null) {
            achievement = achievementRepository.findByTitle(title).orElseThrow(
                    () -> new IllegalArgumentException("Achievement with title " + title + " doesn't exist."));
            addToCache(achievement);
        }
        return achievement;
    }

    public void addToCache(Achievement achievement) {
        achievementCache.put(achievement.getTitle(), achievement);
    }
}
