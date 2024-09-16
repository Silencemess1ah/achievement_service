package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class AchievementCache {
    private final AchievementRepository achievementRepository;
    private Map<String, Achievement> achievementCache;

    @PostConstruct
    public void init() {
        achievementCache = new ConcurrentHashMap<>();
        achievementRepository.findAll().forEach(achievement ->
                achievementCache.put(achievement.getTitle(), achievement));
        log.info("Achievement cache has been filled. Size: {}", achievementCache.size());
    }

    public Achievement get(String title) {
        Achievement achievement = achievementCache.get(title);
        if (achievement == null) {
            achievement = achievementRepository.findByTitle(title).orElseThrow(
                    () -> new EntityNotFoundException("Achievement with title " + title + " doesn't exist."));
            addToCache(achievement);
        }
        return achievement;
    }

    public void addToCache(Achievement achievement) {
        achievementCache.put(achievement.getTitle(), achievement);
    }
}
