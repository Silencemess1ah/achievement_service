package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public abstract class RedisAbstractMessageListener<T> implements MessageListener {
    private final ObjectMapper objectMapper;
    private final Class<T> clazz;
    private final List<EventHandler<T>> abstractEventHandlers;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            T t = objectMapper.readValue(message.getBody(), clazz);
            abstractEventHandlers.forEach(handler -> handler.checkAchievement(t));
            countEvent(t);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void countEvent(T t);
}
