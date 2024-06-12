package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.SkillAcquiredEvent;
import faang.school.achievement.handlers.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SkillEventListener implements MessageListener {

    private final ObjectMapper objectMapper;
    private final List<EventHandler> handlers;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("Received message from channel {}: {}", new String(message.getChannel()), new String(message.getBody()));
        try {
            SkillAcquiredEvent skillAcquiredEvent = objectMapper.readValue(message.getBody(), SkillAcquiredEvent.class);
            handlers.stream().forEach(handler -> handler.handleAchievement(skillAcquiredEvent));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
