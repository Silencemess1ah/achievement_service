package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@RequiredArgsConstructor
@Data
public class AchievementCache {

    private final AchievementRepository achievementRepository;
    private final ConcurrentMap<String, Achievement> achievements = new ConcurrentHashMap<>();


    @PostConstruct
    public void initCache() {
        Iterable<Achievement> allAchievements = achievementRepository.findAll();
        for (Achievement achievement : allAchievements) {
            achievements.putIfAbsent(achievement.getTitle(), achievement);
        }
    }

    public Achievement get(String title) {
        return achievements.get(title);
    }

    public Collection<Achievement> getAll() {
        return achievements.values();
    }
}
