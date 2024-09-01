package faang.school.achievement.service.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.event.CommentEvent;
import faang.school.achievement.service.handler.commentEvent.CommentEventHandler;
import faang.school.achievement.service.handler.commentEvent.EvilCommenterAchievementHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.Message;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentEventListenerTest {
    @Mock
    private CommentEventHandler handler = Mockito.mock(EvilCommenterAchievementHandler.class);
    @Spy
    private ObjectMapper objectMapper;
    List<CommentEventHandler> handlers;

    private CommentEventListener listener;
    @Captor
    private ArgumentCaptor<CommentEvent> eventCaptor;
    private String json = """
                            {
                                "id":1,
                                "authorId":2,
                                "postId":3,
                                "content":"content"
                            }""";
    private Message message;
    private CommentEvent event;

    @BeforeEach
    void setUp() {
        handlers = List.of(handler);
        listener = new CommentEventListener(objectMapper, handlers);
        //Arrange
        message = new Message() {
            @Override
            public byte[] getBody() {
                return json.getBytes();
            }

            @Override
            public byte[] getChannel() {
                return new byte[0];
            }
        };

        event = CommentEvent.builder()
                .id(1L)
                .authorId(2L)
                .postId(3L)
                .content("content")
                .build();
    }

    @Test
    void testOnMessageValid() throws JsonProcessingException {
        // when
        listener.onMessage(message, new byte[]{});
        // then
        verify(handler, times(1)).process(eventCaptor.capture());
        assertEquals(event, eventCaptor.getValue());
    }

    @Test
    void testOnMessageInvalid() throws IOException {
        //Act
        doThrow(RuntimeException.class).when(objectMapper).readValue(message.getBody(), CommentEvent.class);
        //then
        assertThrows(RuntimeException.class, () -> listener.onMessage(message, new byte[]{}));
    }
}
