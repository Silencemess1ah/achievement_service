package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AchievementCache {

    private final Map<String, Achievement> achievements = new HashMap<>();
    private final AchievementRepository achievementRepository;

    @PostConstruct
    public void init() {
        for (Achievement achievement : achievementRepository.findAll()) {
            achievements.put(achievement.getTitle(), achievement);
        }
    }

    public Achievement get(String title) {
        return achievements.get(title);
    }

    public Collection<Achievement> getAll() {
        return achievements.values();
    }

}