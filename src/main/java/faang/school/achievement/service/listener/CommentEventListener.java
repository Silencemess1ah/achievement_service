package faang.school.achievement.service.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.event.CommentEvent;
import faang.school.achievement.service.handler.commentEvent.CommentEventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentEventListener implements MessageListener {
    private final ObjectMapper objectMapper;
    private final List<CommentEventHandler> eventHandlerList;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            CommentEvent commentEvent = objectMapper.readValue(message.getBody(), CommentEvent.class);
            eventHandlerList.forEach(handler -> {
                try {
                    handler.process(commentEvent);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
