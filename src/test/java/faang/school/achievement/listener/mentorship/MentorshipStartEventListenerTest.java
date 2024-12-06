package faang.school.achievement.listener.mentorship;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.achievement.mentorship.MentorshipStartEvent;
import faang.school.achievement.handler.EventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.Message;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MentorshipStartEventListenerTest {

    @Mock
    private Message message;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private EventHandler<MentorshipStartEvent> eventHandler;

    private MentorshipStartEventListener mentorshipStartEventListener;
    private final Map<Class<?>, List<EventHandler<?>>> handlers = new HashMap<>();
    private MentorshipStartEvent mentorshipStartEvent;

    @BeforeEach
    void init() {
        mentorshipStartEventListener = new MentorshipStartEventListener(handlers, objectMapper);

        mentorshipStartEvent = new MentorshipStartEvent();
    }

    @Test
    @DisplayName("Успешная отправка события всем обработчикам")
    void whenListHandlersContainsMessageTypeThenSendMessageToAllHandlers() throws IOException {
        handlers.put(mentorshipStartEvent.getClass(), List.of(eventHandler));
        when(objectMapper.readValue(message.getBody(), MentorshipStartEvent.class))
                .thenReturn(mentorshipStartEvent);

        mentorshipStartEventListener.onMessage(message, new byte[0]);

        verify(objectMapper).readValue(message.getBody(), MentorshipStartEvent.class);
        verify(eventHandler).handle(mentorshipStartEvent);
    }

    @Test
    @DisplayName("Ошибка когда обработчики не найдены")
    void whenListHandlersNotContainsMessageTypeThenThrowException() throws IOException {
        when(objectMapper.readValue(message.getBody(), MentorshipStartEvent.class))
                .thenReturn(mentorshipStartEvent);

        assertThrows(RuntimeException.class,
                () -> mentorshipStartEventListener.onMessage(message, new byte[0]));
    }

    @Test
    @DisplayName("Ошибка когда маппер не может обработать сообщение")
    void whenObjectMapperCantReadMessageThenThrowException() throws IOException {
        when(objectMapper.readValue(message.getBody(), MentorshipStartEvent.class))
                .thenThrow(new IOException());

        assertThrows(RuntimeException.class,
                () -> mentorshipStartEventListener.onMessage(message, new byte[0]));
    }
}