package faang.school.achievement.service.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.event.FollowerEvent;
import faang.school.achievement.service.handler.eventHandler.AbstractEventHandler;
import faang.school.achievement.service.handler.eventHandler.followerEvent.BloggerHandler;
import faang.school.achievement.service.handler.eventHandler.followerEvent.FollowerEventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.connection.Message;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FollowerEventListenerTest {
    @Spy
    private ObjectMapper objectMapper;
    @Mock
    private FollowerEventHandler handler = Mockito.mock(BloggerHandler.class);
    @Mock
    private MessageSource messageSource;
    private final Class<FollowerEvent> clazz = FollowerEvent.class;
    @Captor
    private ArgumentCaptor<FollowerEvent> eventCaptor;
    @Mock
    private Message message;
    private FollowerEventListener followerEventListener;
    private FollowerEvent followerEvent;

    @BeforeEach
    void setUp() {
        List<AbstractEventHandler<FollowerEvent>> handlers = List.of(handler);
        String channelName = "follower_channel";
        followerEventListener = new FollowerEventListener(objectMapper, handlers, messageSource, clazz, channelName);

        String messageBody = "{ \"userId\":1,\"followerId\":2 }";
        followerEvent = FollowerEvent.builder()
                .userId(1L)
                .followerId(2L)
                .build();
        // given
        when(message.getBody()).thenReturn(messageBody.getBytes());
    }

    @Test
    void testOnMessageValid() throws JsonProcessingException {
        // when
        followerEventListener.onMessage(message, new byte[]{});
        // then
        verify(handler, times(1)).process(eventCaptor.capture());
        assertEquals(followerEvent, eventCaptor.getValue());
    }

    @Test
    void testOnMessageInvalid() throws IOException {
        //given
        when(objectMapper.readValue(message.getBody(), FollowerEvent.class)).thenThrow(RuntimeException.class);
        //then
        assertThrows(RuntimeException.class, () -> followerEventListener.onMessage(message, new byte[]{}));
    }
}