package faang.school.achievement.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.ProjectEvent;
import faang.school.achievement.event.AchievementEventDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProjectEventListener {
    private final ObjectMapper objectMapper;
    private final AchievementEventDispatcher eventDispatcher;

    @EventListener
    public void handleMessage(String jsonEvent) {
        ProjectEvent event = readEvent(jsonEvent);
        log.info("Received message from channel: {}", jsonEvent);
        eventDispatcher.dispatchEvent(event);
    }

    private ProjectEvent readEvent(String jsonEvent) {
        try {
            log.info("reading message {}", jsonEvent);
            return objectMapper.readValue(jsonEvent, ProjectEvent.class);
        } catch (JsonProcessingException exception) {
            log.error("message was not downloaded {}", exception.getMessage());
            throw new RuntimeException(exception);
        }
    }
}
