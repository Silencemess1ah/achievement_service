package faang.school.achievement.listener.profilepic;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.profilepic.ProfilePicEvent;
import faang.school.achievement.messaging.handler.EventHandler;
import faang.school.achievement.messaging.listener.profilepic.ProfilePicEventListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.Message;

import java.io.IOException;
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
public class ProfilePicEventListenerTest {
    @Mock
    private EventHandler<ProfilePicEvent> profileEventHandler;
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private ProfilePicEventListener profilePicEventListener;
    @Mock
    private Message redisMessage;
    @Spy
    private List<EventHandler<ProfilePicEvent>> profileEventHandlers;
    private ProfilePicEvent profilePicEvent;

    @BeforeEach
    void setUp() {
        profilePicEvent = new ProfilePicEvent(UUID.randomUUID(), 1L, "avatar-url");
    }

    @Test
    void testOnMessageSuccess() throws IOException {
        when(redisMessage.getBody()).thenReturn("Any".getBytes());
        when(objectMapper.readValue(any(byte[].class), eq(ProfilePicEvent.class)))
                .thenReturn(profilePicEvent);
        when(profileEventHandlers.iterator()).thenReturn(List.of(profileEventHandler).iterator());
        doNothing().when(profileEventHandler).handle(profilePicEvent);

        profilePicEventListener.onMessage(redisMessage, null);

        verify(redisMessage, times(2)).getBody();
        verify(objectMapper, times(1)).readValue(any(byte[].class), eq(ProfilePicEvent.class));
        verify(profileEventHandler).handle(profilePicEvent);
    }

    @Test
    void testOnMessageException() throws IOException {
        when(redisMessage.getBody()).thenReturn("Any".getBytes());
        when(objectMapper.readValue(any(byte[].class), eq(ProfilePicEvent.class)))
                .thenThrow(IOException.class);

        assertThrows(RuntimeException.class, () -> profilePicEventListener.onMessage(redisMessage, new byte[0]));
    }
}