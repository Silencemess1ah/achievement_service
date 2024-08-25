package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.redis.connection.Message;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public abstract class AbstractEventListener<T> {
    private static final String CODE_MESSAGE_ERROR = "message.error.readValueException";
    protected final ObjectMapper objectMapper;
    protected final List<EventHandler<T>> eventHandlers;
    protected final MessageSource messageSource;

    protected void handleEvent(Message message, Class<T> type, Consumer<T> consumer) {
        try {
            T event = objectMapper.readValue(message.getBody(), type);
            consumer.accept(event);
        } catch (IOException e) {
            throw new RuntimeException(messageSource
                    .getMessage(CODE_MESSAGE_ERROR, null, LocaleContextHolder.getLocale()));
        }
    }
}