package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.Rarity;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class AchievementCache {

    private static final ConcurrentHashMap<String, Achievement> achievementCache = new ConcurrentHashMap<>();
    private static final Achievement expert = Achievement.builder()
            .id(1L)
            .title("Expert")
            .description("write 1000 comments")
            .rarity(Rarity.EPIC)
            .build();

    public AchievementCache() {
        achievementCache.put("Expert", expert);
    }

    public Achievement get(String achievementName) {
        return achievementCache.get(achievementName);
    }

}
