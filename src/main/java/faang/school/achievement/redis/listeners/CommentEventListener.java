package faang.school.achievement.redis.listeners;

import faang.school.achievement.config.context.UserContext;
import faang.school.achievement.converters.JsonConverter;
import faang.school.achievement.events.CommentEvent;
import faang.school.achievement.handler.CommentEventHandler;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommentEventListener implements MessageListener {
    private final JsonConverter<CommentEvent> jsonConverter;
    private final List<CommentEventHandler> handlers;
    private final UserContext userContext;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        CommentEvent event = jsonConverter.fromJson(message.toString(), CommentEvent.class);
        userContext.setUserId(event.getAuthorId());
        handlers.forEach(handler -> handler.handle(event));
    }
}
