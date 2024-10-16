package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Getter
@Slf4j
public class AchievementCache {
    private final Map<String, Achievement> cache = new HashMap<>();
    private final AchievementRepository achievementRepository;

    @PostConstruct
    private void fillCache() {
        Iterable<Achievement> achievements = achievementRepository.findAll();
        achievements.forEach(achievement -> cache.put(achievement.getTitle(), achievement));
        log.info("Achievements cached successfully. Size of cache is {}", cache.size());
    }

    public Achievement getByTitle(String title) {
        return Optional.ofNullable(cache.get(title))
                .orElseThrow(() -> new EntityNotFoundException("Achievement with title %s did not found".formatted(title)));
    }
}
