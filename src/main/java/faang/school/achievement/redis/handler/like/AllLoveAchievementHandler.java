package faang.school.achievement.redis.handler.like;

import faang.school.achievement.dto.LikeEventDto;
import faang.school.achievement.redis.handler.EventHandler;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AllLoveAchievementHandler extends EventHandler {
    @Value("${achievement.all-love.title}")
    private String achievementTitle;

    public AllLoveAchievementHandler(AchievementService achievementService, AchievementCache achievementCache) {
        super(achievementService, achievementCache);
    }

    @PostConstruct
    public void setUp() {
        achievement = achievementCache.get(achievementTitle);
    }

    @Override
    public Class<LikeEventDto> getHandledEventType() {
        return LikeEventDto.class;
    }
}
