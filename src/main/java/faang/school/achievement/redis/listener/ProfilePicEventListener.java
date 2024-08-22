package faang.school.achievement.redis.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.model.event.ProfilePicEvent;
import faang.school.achievement.service.eventhandler.AbstractEventHandler;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProfilePicEventListener implements MessageListener {

    private final ObjectMapper objectMapper;
    private final List<AbstractEventHandler<ProfilePicEvent>> handlers;

    @SneakyThrows
    @Override
    public void onMessage(@Nonnull Message message, byte[] pattern) {
        log.info("{} received message: {}", this.getClass().getName(), message);
        log.debug("Channel: {}", new String(pattern, StandardCharsets.UTF_8));

        ProfilePicEvent profilePicEvent = objectMapper.readValue(message.getBody(), ProfilePicEvent.class);

        handlers.forEach(handler -> handler.handle(profilePicEvent));
    }
}
