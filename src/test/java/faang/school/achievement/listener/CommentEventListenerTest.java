package faang.school.achievement.listener;


import faang.school.achievement.dto.CommentEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentEventListenerTest {

    @Spy
    private ObjectMapper mapper;

    @Mock
    private EventDispatcher eventDispatcher;

    @InjectMocks
    private CommentEventListener commentEventListener;

    private Message message;
    private CommentEvent commentEvent;

    @BeforeEach
    public void setUp() {
        commentEvent = new CommentEvent(1L, 1L, 1L, "Comment");
        message = mock(Message.class);

        when(message.getChannel()).thenReturn("comment-channel".getBytes());
        when(message.getBody()).
                thenReturn("{\"idPost\": 1, \"idComment\": 1, \"idAuthor\": 1, \"comment\": \"Comment\"}".getBytes());
    }

    @Test
    public void testOnMessage() throws IOException {
        when(mapper.readValue(message.getBody(), CommentEvent.class)).thenReturn(commentEvent);
        commentEventListener.onMessage(message, null);
        verify(eventDispatcher).handleEvent(commentEvent);
    }
}
