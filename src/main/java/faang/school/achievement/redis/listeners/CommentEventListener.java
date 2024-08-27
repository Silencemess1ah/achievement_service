package faang.school.achievement.redis.listeners;

import faang.school.achievement.converters.JsonConverter;
import faang.school.achievement.events.CommentEvent;
import faang.school.achievement.handler.CommentEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommentEventListener implements MessageListener {
    private final JsonConverter<CommentEvent> jsonConverter;
    private final List<CommentEventHandler> handlers;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        CommentEvent event = jsonConverter.fromJson(message.toString(), CommentEvent.class);
        handlers.forEach(handler -> handler.handle(event));
    }
}
