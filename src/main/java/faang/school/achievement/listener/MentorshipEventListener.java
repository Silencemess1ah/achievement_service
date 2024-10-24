package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.MentorshipStartEventDto;
import faang.school.achievement.handler.mentorship_event.MentorshipEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MentorshipEventListener implements MessageListener {

    private final ObjectMapper objectMapper;
    private final List<MentorshipEventHandler> handlers;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            MentorshipStartEventDto event = objectMapper.readValue(message.getBody(), MentorshipStartEventDto.class);
            handlers.forEach(mentorshipEventHandler -> mentorshipEventHandler.handle(event));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
