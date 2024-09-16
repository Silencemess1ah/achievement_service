package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.CommentAchievementEvent;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class ExpertAchievementHandler extends AbstractAchievementHandler<CommentAchievementEvent> {

    @Value("${achievement-handler.comment-achievement-handler.achievement-name}")
    private String achievementTitle;
    @Value("${achievement-handler.comment-achievement-handler.points}")
    private Long achievementPoints;

    public ExpertAchievementHandler(AchievementService achievementService,
                                    AchievementCache achievementCache) {
        super(achievementCache, achievementService);
    }

    @Override
    protected String getAchievementTitle() {
        return achievementTitle;
    }

    @Override
    protected long getUserId(CommentAchievementEvent event) {
        return event.getAuthorId();
    }

    @Override
    protected long getPointsToEarnAchievement() {
        return achievementPoints;
    }

}