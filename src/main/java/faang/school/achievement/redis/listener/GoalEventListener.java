package faang.school.achievement.redis.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.GoalSentEventDto;
import faang.school.achievement.redis.handler.goal.GoalEventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GoalEventListener implements MessageListener {
    private final List<GoalEventHandler> handlers;
    private final ObjectMapper objectMapper;
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try{
            GoalSentEventDto goalSentEventDto = objectMapper.readValue(message.getBody(), GoalSentEventDto.class);
            handlers.forEach(handler -> handler.handleEvent(goalSentEventDto));
        } catch (IOException ex) {
            throw new RuntimeException("Message read failed");
        }
    }
}
