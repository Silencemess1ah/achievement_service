package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.CommentEvent;
import faang.school.achievement.handler.EventHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentEventListener extends AbstractEventListener<CommentEvent> {
    public CommentEventListener(ObjectMapper objectMapper,
                                List<EventHandler<CommentEvent>> eventHandlers,
                                Class<CommentEvent> type) {
        super(objectMapper, eventHandlers, type);
    }
}

//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class CommentEventListener implements MessageListener {
//    private final List<EventHandler> handlers;
//    private final ObjectMapper objectMapper;
//
//    @Override
//    public void onMessage(Message message, byte[] pattern) {
//        try {
//            CommentEvent commentEvent = objectMapper.readValue(message.getBody(), CommentEvent.class);
//            for (EventHandler handler : handlers) {
//                if (handler != null) {
//                    handler.checkAndGetAchievement(commentEvent);
//                }
//            }
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//        }
//    }
//}
