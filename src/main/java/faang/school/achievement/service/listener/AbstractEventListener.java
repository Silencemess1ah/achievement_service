package faang.school.achievement.service.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.event.EventInt;
import faang.school.achievement.exception.CustomJsonProcessingException;
import faang.school.achievement.exception.CustomReadValueException;
import faang.school.achievement.service.handler.eventHandler.AbstractEventHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
public abstract class AbstractEventListener<T extends EventInt> implements MessageListener {
    private static final String CODE_MESSAGE_ERROR = "message.error.readValueException";
    private final ObjectMapper objectMapper;
    protected final List<AbstractEventHandler<T>> eventHandlers;
    private final MessageSource messageSource;
    private final Class<T> clazz;
    @Getter
    private final String channelName;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            T event = objectMapper.readValue(message.getBody(), clazz);
            eventHandlers.forEach(handler -> {
                try {
                    handler.process(event);
                } catch (JsonProcessingException e) {
                    throw new CustomJsonProcessingException(e);
                }
            });
        } catch (IOException e) {
            String msg = messageSource.getMessage("message.error.readValueException", null, Locale.getDefault());
            throw new CustomReadValueException(msg, e);
        }
    }
}