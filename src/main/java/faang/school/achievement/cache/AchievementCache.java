package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AchievementCache {
    private final Map<String, Achievement> cache = new HashMap<>();
    private final AchievementRepository achievementRepository;

    @PostConstruct
    public void fillCache() {
        Iterable<Achievement> achievements = achievementRepository.findAll();
        achievements.forEach(achievement -> cache.put(achievement.getTitle(), achievement));
        log.info("Achievements cached successfully. Size of cache is {}", cache.size());
    }

    public Optional<Achievement> getByTitle(String title) {
        return Optional.ofNullable(cache.get(title));
    }
}
