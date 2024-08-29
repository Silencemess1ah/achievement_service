package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.CommentEventDto;
import faang.school.achievement.handler.EventHandler;
import lombok.NonNull;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentEventListener extends AbstractEventListener<CommentEventDto> {

    private final List<EventHandler<CommentEventDto>> handlers;

    public CommentEventListener(ObjectMapper objectMapper, List<EventHandler<CommentEventDto>> handlers) {
        super(objectMapper);
        this.handlers = handlers;
    }

    @Override
    public void onMessage(@NonNull Message message, byte[] pattern) {
        handleEvent(CommentEventDto.class, message);
    }

    @Override
    public List<EventHandler<CommentEventDto>> getHandlers(CommentEventDto event) {
        return handlers;
    }
}