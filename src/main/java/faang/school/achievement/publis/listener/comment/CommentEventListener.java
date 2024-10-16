package faang.school.achievement.publis.listener.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.comment.CommentEventDto;
import faang.school.achievement.service.handlers.EventHandler;
import faang.school.achievement.publis.AbstractEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CommentEventListener extends AbstractEventListener<CommentEventDto> implements MessageListener {

    public CommentEventListener(ObjectMapper objectMapper, List<EventHandler<CommentEventDto>> commentHandlers) {
        super(objectMapper, commentHandlers);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String messageBody = new String(message.getBody());
        log.info("Received messageBody: " + messageBody);

        CommentEventDto commentEvent = mapToEvent(messageBody, CommentEventDto.class);

        handleEvent(commentEvent);
    }
}
