package faang.school.achievement.service.handler.eventHandler.followerEvent;

import faang.school.achievement.dto.event.FollowerEvent;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.handler.eventHandler.AbstractEventHandler;
import faang.school.achievement.service.publisher.AchievementPublisher;

public abstract class FollowerEventHandler extends AbstractEventHandler<FollowerEvent> {

    public FollowerEventHandler(AchievementCache cache, AchievementService service, AchievementPublisher publisher,
                                String nameAchievement, AchievementMapper mapper) {
        super(cache, service, publisher, nameAchievement, mapper);
    }
}
