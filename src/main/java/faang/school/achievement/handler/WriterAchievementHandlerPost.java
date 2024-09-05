package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.PostEvent;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WriterAchievementHandlerPost extends AbstractAchievementHandler<PostEvent> {

    @Value("${achievement-handler.writer-achievement-handler.achievement-name}")
    private String achievementTitle;
    @Value("${achievement-handler.writer-achievement-handler.points}")
    private long pointsToEarnAchievement;

    public WriterAchievementHandlerPost(AchievementCache achievementCache, AchievementService achievementService) {
        super(achievementCache, achievementService);
    }

    @Override
    protected String getAchievementTitle() {
        return achievementTitle;
    }

    @Override
    protected long getUserId(PostEvent event) {
        return event.getAuthorId();
    }

    @Override
    protected long getPointsToEarnAchievement() {
        return pointsToEarnAchievement;
    }
}
