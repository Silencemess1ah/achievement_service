package faang.school.achievement.messaging.listener.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.project.ProjectEvent;
import faang.school.achievement.messaging.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProjectEventListener {
    private final List<EventHandler<ProjectEvent>> projectEventHandlers;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${spring.kafka.topics.project_topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void onMessage(String data) {
        ProjectEvent projectEvent;

        try {
            projectEvent = objectMapper.readValue(data.getBytes(), ProjectEvent.class);
            log.info("Received event: {}", projectEvent);
        } catch (IOException e) {
            String errorMessage = "Failed reading event: " + data;
            log.error(errorMessage);
            throw new RuntimeException(String.format("%s, %s", errorMessage, e));
        }

        projectEventHandlers.forEach(handler -> handler.handle(projectEvent));
    }
}
