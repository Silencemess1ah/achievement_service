package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.CommentEvent;
import faang.school.achievement.handler.EventHandler;
import faang.school.achievement.model.EventType;
import faang.school.achievement.service.UserEventCounterService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentMessageListener extends RedisAbstractMessageListener<CommentEvent> {
    private final UserEventCounterService userEventCounterService;

    public CommentMessageListener(ObjectMapper objectMapper,
                                  List<EventHandler<CommentEvent>> abstractEventHandlers,
                                  UserEventCounterService userEventCounterService) {
        super(objectMapper, CommentEvent.class, abstractEventHandlers);
        this.userEventCounterService = userEventCounterService;
    }

    @Override
    public void countEvent(CommentEvent commentEvent) {
        if (userEventCounterService.hasProgress(commentEvent.getPostAuthorId(), EventType.COMMENT_EVENT)) {
            userEventCounterService.updateAllProgress(commentEvent.getPostAuthorId(), EventType.COMMENT_EVENT);
        } else {
            userEventCounterService.addNewAllProgress(commentEvent.getPostAuthorId(), EventType.COMMENT_EVENT);
        }
    }
}