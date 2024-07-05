package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.MentorshipStartEvent;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class MentorshipEventListener implements MessageListener {

    private ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            MentorshipStartEvent mentorshipStartEvent = objectMapper.readValue(message.getBody(), MentorshipStartEvent.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
