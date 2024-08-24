package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractEventListener<T> implements MessageListener {
    private final ObjectMapper objectMapper;
    private final List<EventHandler<T>> eventHandlers;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            System.out.println("message was got");
            T mentorshipStartEvent = objectMapper.readValue(message.getBody(), getInstance());
            eventHandlers.forEach(eventHandler -> eventHandler.handleEvent(mentorshipStartEvent));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract Class<T> getInstance();
}
