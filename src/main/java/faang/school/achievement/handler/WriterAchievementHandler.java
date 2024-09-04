package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.event.PostEventDto;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WriterAchievementHandler extends AbstractAchievementHandler<PostEventDto> implements EventHandler<PostEventDto> {

    @Value("${handler.achievement.names.writer}")
    private String achievementTitle;

    public WriterAchievementHandler(AchievementService achievementService, AchievementCache achievementCache) {
        super(achievementService, achievementCache);
    }

    @Override
    public void handle(PostEventDto event) {
        handleAchievement(event.getUserId(), achievementTitle);
    }

    @Override
    public Class<PostEventDto> getType() {
        return PostEventDto.class;
    }
}
