package faang.school.achievement.listener;

import com.google.protobuf.InvalidProtocolBufferException;
import faang.school.achievement.event.Event;
import faang.school.achievement.event.FollowerEvent;
import faang.school.achievement.event.handler.EventHandlerManager;
import faang.school.achievement.mapper.FollowerEventMapper;
import faang.school.achievement.protobuf.generate.FollowerEventProto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FollowerEventListenerTest {

    @Mock
    private FollowerEventMapper followerEventMapper;

    @Mock
    private EventHandlerManager<Event> eventHandlerManager;

    @InjectMocks
    private FollowerEventListener followerEventListener;

    private byte[] testByteEvent;
    private FollowerEventProto.FollowerEvent protoEvent;
    private FollowerEvent followerEvent;

    @BeforeEach
    void setUp() {
        protoEvent = FollowerEventProto.FollowerEvent.newBuilder().build();
        testByteEvent = protoEvent.toByteArray();
        followerEvent = new FollowerEvent(LocalDateTime.now(), 1941L);
    }

    @Test
    void onMessage_shouldProcessEvent() throws InvalidProtocolBufferException {
        when(followerEventMapper.toFollowerEvent(protoEvent)).thenReturn(followerEvent);

        followerEventListener.onMessage(testByteEvent);

        verify(followerEventMapper).toFollowerEvent(protoEvent);
        verify(eventHandlerManager).processEvent(followerEvent);
    }

    @Test
    void onMessage_shouldHandleInvalidProtocolBufferException() {
        assertDoesNotThrow(() -> followerEventListener.onMessage(new byte[0]));
    }
}
