package faang.school.achievement.service.handler.eventHandler.followerEvent;

import faang.school.achievement.dto.event.EventInt;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.publisher.AchievementPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BloggerHandler extends FollowerEventHandler {

    public BloggerHandler(AchievementCache cache,
                          AchievementService service,
                          AchievementPublisher achievementPublisher,
                          @Value("${data.achievements.titles.blogger}") String achievementsTitle,
                          AchievementMapper achievementMapper) {
        super(cache, service, achievementPublisher, achievementsTitle, achievementMapper);
    }

    @Override
    public boolean checkConditionForInc(EventInt event) {
        return true;
    }
}
