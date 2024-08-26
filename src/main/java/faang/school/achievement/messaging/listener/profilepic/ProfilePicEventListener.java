package faang.school.achievement.messaging.listener.profilepic;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.profilepic.ProfilePicEvent;
import faang.school.achievement.exception.ExceptionMessage;
import faang.school.achievement.messaging.handler.EventHandler;
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
public class ProfilePicEventListener implements MessageListener {
    private final List<EventHandler<ProfilePicEvent>> profileEventHandlers;
    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            ProfilePicEvent profilePicEvent = objectMapper.readValue(message.getBody(), ProfilePicEvent.class);
            log.info("Message received: {}", message.getBody());
            profileEventHandlers.forEach(handler -> handler.handle(profilePicEvent));
            log.info("Achievement {} sent for processing", profilePicEvent);
        } catch (IOException e) {
            log.error(ExceptionMessage.EVENT_HANDLING_FAILURE, e);
            throw new IllegalStateException(ExceptionMessage.EVENT_HANDLING_FAILURE, e);
        }
    }
}
