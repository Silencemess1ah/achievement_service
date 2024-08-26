package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.StreamSupport;

@Slf4j
@Component
@RequiredArgsConstructor
public class AchievementCache {

    private static final ConcurrentHashMap<String, Achievement> CACHE = new ConcurrentHashMap<>();
    private final AchievementRepository achievementRepository;

    @PostConstruct
    public void fill() {
        StreamSupport.stream(achievementRepository.findAll().spliterator(), false)
                .forEach(achievement -> {
                    CACHE.put(achievement.getTitle(), achievement);
                });
        log.info("Achievement cache has been filled. Size: {}", CACHE.size());
    }

    public Achievement get(String achievementName) {
        return CACHE.get(achievementName);
    }

    public void add(Achievement achievement) {
        CACHE.put(achievement.getTitle(), achievement);
    }

    public ConcurrentHashMap<String, Achievement> getCache() {
        return CACHE;
    }

}
