package faang.school.achievement.redis.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.model.event.Event;
import faang.school.achievement.service.eventhandler.AbstractEventHandler;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractEventListener<T extends Event> implements MessageListener {

    private final ObjectMapper objectMapper;
    private final List<AbstractEventHandler<T>> handlers;

    @SneakyThrows
    @Override
    public void onMessage(@Nonnull Message message, byte[] pattern) {
        log.info("{} received message: {}", this.getClass().getName(), message);
        log.debug("Channel: {}", new String(pattern, StandardCharsets.UTF_8));

        T profilePicEvent = objectMapper.readValue(message.getBody(), getType());

        handlers.forEach(handler -> handler.handle(profilePicEvent));
    }

    protected abstract Class<T> getType();
}
