package faang.school.achievement.redis.handler.like;

import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AllLoveAchievementHandler extends LikeEventHandler {
    @Value("${achievement.all-love.title}")
    private String achievementTitle;
    private final AchievementCache achievementCache;

    public AllLoveAchievementHandler(AchievementService achievementService, AchievementCache achievementCache) {
        super(achievementService);
        this.achievementCache = achievementCache;
    }

    @PostConstruct
    public void setUp() {
        achievement = achievementCache.get(achievementTitle);
    }

}
