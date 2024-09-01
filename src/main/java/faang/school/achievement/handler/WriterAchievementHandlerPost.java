package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.PostEvent;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WriterAchievementHandlerPost extends AbstractAchievementHandler implements EventHandler<PostEvent> {

    @Value("${achievement-handler.writer-achievement-handler.achievement-name}")
    private String achievementTitle;

    public WriterAchievementHandlerPost(AchievementService achievementService, AchievementCache achievementCache) {
        super(achievementService, achievementCache);
    }

    @Override
    public void handleEvent(PostEvent event) {
        processAchievementEvent(achievementTitle, event.getPostId());
    }

    @Override
    public Class<PostEvent> getType() {
        return PostEvent.class;
    }
}
