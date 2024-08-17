package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AchievementCache {

    private final AchievementRepository achievementRepository;
    private final Map<String, Achievement> achievementMap = new HashMap<>();

    @PostConstruct
    private void createCache() {
        achievementRepository.findAll()
                .forEach(achievement -> achievementMap.put(achievement.getTitle(), achievement));
    }

    public Achievement getAchievement(String achievementTitle) {
        return achievementMap.get(achievementTitle);
    }
}