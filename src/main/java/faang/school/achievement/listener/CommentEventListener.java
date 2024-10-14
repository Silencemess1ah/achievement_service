package faang.school.achievement.listener;


import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.CommentEvent;
import faang.school.achievement.service.CommentEventHandler;
import faang.school.achievement.service.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentEventListener implements MessageListener {

    private final ObjectMapper objectMapper;
    private final List<EventHandler<CommentEvent>> handlers;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());
        String body = new String(message.getBody());

        log.info("Received message body: {}", body);

        try {
            CommentEvent event = objectMapper.readValue(message.getBody(), CommentEvent.class);
            handlers.forEach(handler -> handler.handleEvent(event));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }

        log.info("Received message channel: {}", channel);
    }
}
