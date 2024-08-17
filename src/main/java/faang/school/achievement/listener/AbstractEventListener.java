package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.type.SerializationException;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
abstract public class AbstractEventListener<T>{
    protected final ObjectMapper objectMapper;
    protected final List<EventHandler<T>> eventHandlers;

    protected T mapEvent(Message message, Class<T> eventType){
        try {
            return objectMapper.readValue(message.getBody(), eventType);
        } catch (IOException e) {
            log.error("Couldn't read value from message: {}, at Event Type: {}", message.getBody(), eventType.getName());
            throw new SerializationException("Couldn't serialize message", e);
        }
    }
}
