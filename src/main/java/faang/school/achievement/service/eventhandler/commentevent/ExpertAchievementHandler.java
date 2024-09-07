package faang.school.achievement.service.eventhandler.commentevent;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.events.CommentEvent;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.eventhandler.AbstractEventHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ExpertAchievementHandler extends AbstractEventHandler<CommentEvent> {

    public ExpertAchievementHandler(@Value("${achievements.expert}") String achievementTitle,
                                    AchievementCache achievementCache,
                                    AchievementService achievementService) {

        super(achievementTitle, achievementCache, achievementService);
    }
}
