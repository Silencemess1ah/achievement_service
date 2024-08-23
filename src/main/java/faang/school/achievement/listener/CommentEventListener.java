package faang.school.achievement.listener;


import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.CommentEvent;
import faang.school.achievement.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class CommentEventListener implements MessageListener {
    private final List<EventHandler> handlers;
    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            CommentEvent commentEvent = objectMapper.readValue(message.getBody(), CommentEvent.class);
            for (EventHandler handler : handlers) {
                if (handler != null) {
                    handler.checkAndGetAchievement(commentEvent);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}