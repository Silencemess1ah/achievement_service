package faang.school.achievement.listener.like;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.like.LikeEvent;
import faang.school.achievement.messaging.handler.EventHandler;
import faang.school.achievement.messaging.listener.like.LikeEventListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.Message;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LikeEventListenerTest {

    @InjectMocks
    private LikeEventListener likeEventListener;

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private EventHandler<LikeEvent> likeEventHandler;
    @Mock
    private Message message;
    @Spy
    private List<EventHandler<LikeEvent>> likeEventHandlers;

    private LikeEvent likeEvent;

    @BeforeEach
    void setUp() {
        likeEvent = new LikeEvent(UUID.randomUUID(),1, 1, 1, LocalDateTime.now());
    }

    @Test
    void testOnMessageShouldThrow() throws IOException {
        when(message.getBody()).thenReturn("Any".getBytes());
        when(objectMapper.readValue(any(byte[].class), eq(LikeEvent.class))).thenThrow(IOException.class);

        assertThrows(RuntimeException.class, () -> likeEventListener.onMessage(message, new byte[0]));
    }

    @Test
    void testOnMessageSuccess() throws IOException {
        when(message.getBody()).thenReturn("Any".getBytes());
        when(objectMapper.readValue(any(byte[].class), eq(LikeEvent.class))).thenReturn(likeEvent);
        when(likeEventHandlers.iterator()).thenReturn(List.of(likeEventHandler).iterator());
        doNothing().when(likeEventHandler).handle(likeEvent);

        likeEventListener.onMessage(message, null);

        verify(message, times(1)).getBody();
        verify(objectMapper, times(1)).readValue(any(byte[].class), eq(LikeEvent.class));
        verify(likeEventHandler).handle(likeEvent);
    }
}