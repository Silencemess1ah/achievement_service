package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.CommentEventDto;
import faang.school.achievement.handler.CommentEventHandler;
import lombok.NonNull;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentEventListener extends AbstractEventListener<CommentEventDto> {

    private final List<CommentEventHandler> handlers;

    public CommentEventListener(ObjectMapper objectMapper, List<CommentEventHandler> handlers) {
        super(objectMapper);
        this.handlers = handlers;
    }

    @Override
    public void onMessage(@NonNull Message message, byte[] pattern) {
        CommentEventDto commentEvent = handleEvent(CommentEventDto.class, message);
        handlers.forEach(handler -> handler.handle(commentEvent));
    }
}
