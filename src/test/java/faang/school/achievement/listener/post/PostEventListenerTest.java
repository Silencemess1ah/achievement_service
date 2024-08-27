package faang.school.achievement.listener.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.post.PostEvent;
import faang.school.achievement.messaging.handler.AbstractEventHandler;
import faang.school.achievement.messaging.listener.post.PostEventListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.Message;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostEventListenerTest {
    @Mock
    private List<AbstractEventHandler> abstractEventHandlers;
    @Mock
    private AbstractEventHandler opinionLeaderAchievementHandler;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private Message message;
    @InjectMocks
    private PostEventListener postEventListener;
    private PostEvent postEvent;
    String jsonString = "{\"id\":1,\"authorId\":2}";

    @BeforeEach
    void init() {
        postEvent = PostEvent.builder()
                .id(1L)
                .authorId(2L)
                .build();
    }

    @Test
    void testReadValueException() throws IOException {
        when(message.getBody()).thenReturn(jsonString.getBytes());
        when(objectMapper.readValue(any(byte[].class), eq(PostEvent.class)))
                .thenThrow(new RuntimeException("ошибка"));

        Exception exception = assertThrows(RuntimeException.class, () ->
                postEventListener.onMessage(message, null));

        assertEquals("ошибка", exception.getMessage());
    }

    @Test
    void testHandle() throws IOException {
        when(abstractEventHandlers.iterator())
                .thenReturn(List.of(opinionLeaderAchievementHandler).iterator());
        when(message.getBody()).thenReturn(jsonString.getBytes());
        when(objectMapper.readValue(any(byte[].class), eq(PostEvent.class)))
                .thenReturn(postEvent);
        doNothing().when(opinionLeaderAchievementHandler)
                .handle(postEvent);

        postEventListener.onMessage(message, null);

        verify(opinionLeaderAchievementHandler, times(1)).handle(postEvent);
    }
}