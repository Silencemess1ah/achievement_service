package faang.school.achievement.service.handler.eventHandlerImpl;

import faang.school.achievement.dto.event.CommentEvent;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.handler.EventHandler;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public abstract class CommentEventHandler implements EventHandler<CommentEvent> {
    protected final AchievementCache cache;
    protected final AchievementService service;
}
