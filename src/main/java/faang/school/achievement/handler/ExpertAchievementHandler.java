package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.CommentEvent;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class ExpertAchievementHandler extends AbstractAchievementHandler implements EventHandler<CommentEvent> {

    @Value("${achievement-handler.comment-achievement-handler.achievement-name}")
    private String achievementTitle;

    public ExpertAchievementHandler(AchievementService achievementService,
                                    AchievementCache achievementCache) {
        super(achievementService, achievementCache);
    }

    @Override
    public void handleEvent(CommentEvent event) {
        processAchievementEvent(achievementTitle, event.getAuthorId());
    }

    @Override
    public Class<CommentEvent> getType() {
        return CommentEvent.class;
    }
}