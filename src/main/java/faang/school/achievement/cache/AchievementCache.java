package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.validator.AchievementValidator;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
@RequiredArgsConstructor
public class AchievementCache {

    private final AchievementRepository achievementRepository;
    private final AchievementValidator achievementValidator;

    private final Map<String, Achievement> achievements = new HashMap<>();

    @PostConstruct
    public void init() {
        Iterable<Achievement> allAchievements = achievementRepository.findAll();
        allAchievements.forEach(achievement -> achievements.put(achievement.getTitle(), achievement));
    }

    public Achievement getAchievement(String name) {
        achievementValidator.checkIsNullOrEmpty(name);
        return achievements.get(name);
    }
}