package faang.school.achievement.listener.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.post.PostEvent;
import faang.school.achievement.exception.ExceptionMessage;
import faang.school.achievement.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class PostEventListener implements MessageListener {
    private final List<EventHandler<PostEvent>> postEventHandlers;
    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            PostEvent postEvent = objectMapper.readValue(message.getBody(), PostEvent.class);
            log.info("Received message: {}", message.getBody());
            for (var postEventHandler : postEventHandlers) {
                postEventHandler.handle(postEvent);
            }
            log.info("Achievement {} sent for processing", postEvent);
        } catch (IOException e) {
            log.error(ExceptionMessage.EVENT_HANDLING_FAILURE, e);
            throw new IllegalStateException(ExceptionMessage.EVENT_HANDLING_FAILURE, e);
        }
    }
}