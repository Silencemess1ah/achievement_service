package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.MentorshipStartEventDto;
import faang.school.achievement.handler.AbstractEventHandler;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MentorshipEventListener extends AbstractEventListener<MentorshipStartEventDto> {

    public MentorshipEventListener(List<AbstractEventHandler<MentorshipStartEventDto>> abstractEventHandlers,
                                   ObjectMapper objectMapper) {
        super(abstractEventHandlers, objectMapper);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        processEvent(message, MentorshipStartEventDto.class);
    }
}
