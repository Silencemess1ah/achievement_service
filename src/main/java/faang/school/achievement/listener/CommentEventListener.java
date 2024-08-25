package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.model.CommentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class CommentEventListener extends AbstractListener<CommentEvent> {

    public CommentEventListener(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public void handleEvent(Message message) {

    }
}
