package faang.school.achievement.redis.handler.goal;

import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CollectorAchievementHandlerDeprecated extends GoalEventHandler {

    @Value("${achievement.collector.title}")
    private String achievementTitle;
    private final AchievementCache achievementCache;

    public CollectorAchievementHandlerDeprecated(AchievementService achievementService, AchievementCache achievementCache) {
        super(achievementService);
        this.achievementCache = achievementCache;
    }

    @PostConstruct
    public void setUp() {
        achievement = achievementCache.get(achievementTitle);
    }

}
