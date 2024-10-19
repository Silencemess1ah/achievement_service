package faang.school.achievement.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.ProjectEvent;
import faang.school.achievement.event.AchievementEventDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProjectEventListener implements MessageListener {
    private final ObjectMapper objectMapper;
    private final AchievementEventDispatcher<ProjectEvent> eventDispatcher;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("Received message from channel: {}", message.toString());
        try {
            String body = new String(message.getBody());
            log.info("reading message {}", body);
            ProjectEvent event = objectMapper.readValue(message.getBody(), ProjectEvent.class);
            eventDispatcher.dispatchEvent(event);
        } catch (JsonProcessingException exception) {
            log.error("message was not downloaded {}", exception.getMessage());
            throw new RuntimeException(exception);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
