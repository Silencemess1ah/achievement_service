package faang.school.achievement.service.handler.eventHandler.commentEvent;

import faang.school.achievement.dto.event.EventInt;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.publisher.AchievementPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EvilCommenterAchievementHandler extends CommentEventHandler {

    @Autowired
    public EvilCommenterAchievementHandler(AchievementCache cache,
                                           AchievementService service,
                                           AchievementPublisher achievementPublisher,
                                           @Value("${data.achievements.titles.evil-commenter}") String nameAchievement,
                                           AchievementMapper achievementMapper) {
        super(cache, service, achievementPublisher, nameAchievement, achievementMapper);
    }


    @Override
    public boolean checkConditionForInc(EventInt event) {
        return true;
    }
}
