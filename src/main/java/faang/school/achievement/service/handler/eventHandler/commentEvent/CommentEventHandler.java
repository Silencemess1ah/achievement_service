package faang.school.achievement.service.handler.eventHandler.commentEvent;

import faang.school.achievement.dto.event.CommentEvent;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.handler.eventHandler.AbstractEventHandler;
import faang.school.achievement.service.publisher.AchievementPublisher;

public abstract class CommentEventHandler extends AbstractEventHandler<CommentEvent> {
    public CommentEventHandler(AchievementCache cache, AchievementService service, AchievementPublisher publisher, String nameAchievement, AchievementMapper mapper) {
        super(cache, service, publisher, nameAchievement, mapper);
    }
}
