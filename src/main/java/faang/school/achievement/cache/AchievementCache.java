package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AchievementCache {
    private static Map<String, Achievement> achievementMap;


    public void createCache(List<Achievement> achievements) {
        achievementMap = achievements.stream()
                .collect(Collectors.toMap(Achievement::getTitle, value -> value));
    }

    public Achievement getAchievement(String achievementTitle) {
        return achievementMap.get(achievementTitle);

    }


}
