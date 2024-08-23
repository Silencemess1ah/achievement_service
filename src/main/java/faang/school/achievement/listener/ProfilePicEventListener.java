package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.ProfilePicEvent;
import faang.school.achievement.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProfilePicEventListener implements MessageListener {
    private final ObjectMapper objectMapper;
    private final List<EventHandler<ProfilePicEvent>> eventHandlers;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            ProfilePicEvent profilePicEvent = objectMapper.readValue(message.getBody(), ProfilePicEvent.class);
            eventHandlers.forEach(handler -> handler.handle(profilePicEvent));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
