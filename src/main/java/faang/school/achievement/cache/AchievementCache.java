package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AchievementCache {

    private final AchievementRepository achievementRepository;
    private final Map<String, Achievement> achievementsByName = new HashMap<>();
    private final Map<Long, Achievement> achievementsById = new HashMap<>();

    @PostConstruct
    public void init() {
        List<Achievement> achievements = achievementRepository.findAll();
        achievements.forEach(achievement -> {
            achievementsByName.put(achievement.getTitle(), achievement);
            achievementsById.put(achievement.getId(), achievement);
        });
    }

    public Optional<Achievement> getAchievement(String title) {
        return Optional.ofNullable(achievementsByName.get(title));
    }

    public Optional<Achievement> getAchievementById(Long id) {
        return Optional.ofNullable(achievementsById.get(id));
    }

    public Map<String, Achievement> getAchievementsByName() {
        return Collections.unmodifiableMap(achievementsByName);
    }

    public Map<Long, Achievement> getAchievementsById() {
        return Collections.unmodifiableMap(achievementsById);
    }
}
