package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AchievementCache {

    private Map<String, Achievement> achievements;
    private final AchievementRepository achievementRepository;

    @PostConstruct
    private void cacheInit() {
        log.info("Achievements cache initialize");
        achievements = achievementRepository.findAll().stream()
                .collect(Collectors.toMap(Achievement::getTitle, achievement -> achievement));
    }

    public Achievement get(String title) {
        return achievements.get(title);
    }

    public void add(Achievement achievement) {
        achievements.put(achievement.getTitle(), achievement);
    }

    public int cacheSize() {
        return achievements.size();
    }
}
