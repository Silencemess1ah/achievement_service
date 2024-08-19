package faang.school.achievement.redis.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.redis.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public  abstract class AbstractEventListener<T> implements MessageListener {

    private final ObjectMapper objectMapper;
    private final List<EventHandler<T>> handlers;
    private final Class<T> type;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            T postEvent = objectMapper.readValue(message.getBody(), type);
            handlers.forEach(handler -> handler.handleEvent(postEvent));
        } catch (IOException e) {
            throw new RuntimeException(String.format("Received message decoding failed: %s", e));
        }
    }
}
