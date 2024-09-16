package faang.school.achievement.listener;

import faang.school.achievement.eventhandler.EventHandler;
import faang.school.achievement.model.CommentEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.Message;

import java.io.IOException;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CommentEventListenerTest {
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private EventHandler<CommentEvent> commentEventHandler;
    @Mock
    private Message message;

    private CommentEventListener commentEventListener;
    private CommentEvent commentEvent;
    private List<EventHandler<CommentEvent>> eventHandlers;
    private long userId;
    private long commentId;
    private long postId;
    private String content;
    private byte[] pattern;

    @BeforeEach
    public void setUp() {
        eventHandlers = List.of(commentEventHandler);
        commentEventListener = new CommentEventListener(objectMapper, eventHandlers);
        userId = 1L;
        commentId = 2L;
        postId = 3L;
        content = "This is a comment";
        commentEvent = new CommentEvent(userId, commentId, postId, content);
        pattern = "This is a comment".getBytes();
    }

    @Test
    public void testHandleEvent() throws IOException {
        Mockito.when(message.getBody()).thenReturn(pattern);
        Mockito.when(objectMapper.readValue(message.getBody(), CommentEvent.class)).thenReturn(commentEvent);
        commentEventListener.handleEvent(message);
        Mockito.verify(objectMapper).readValue(message.getBody(), CommentEvent.class);
    }

}
