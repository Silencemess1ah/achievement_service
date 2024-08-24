package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.TeamEvent;
import faang.school.achievement.handler.EventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.Message;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TeamEventListenerTest {
    private static final String MESSAGE_ERROR = "ReadValue exception";
    private static final long VALID_ID = 1L;
    private final String string = "\"teamId\":1,\"userId\":2,\"projectId\":3";
    private TeamEvent event;
    private Message message;
    @Mock
    private List<EventHandler<TeamEvent>> eventHandlers;
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private TeamEventListener listener;

    @BeforeEach
    void setUp() {
        //Arrange
        message = new Message() {
            @Override
            public byte[] getBody() {
                return string.getBytes();
            }

            @Override
            public byte[] getChannel() {
                return new byte[0];
            }
        };
        event = TeamEvent.builder()
                .teamId(VALID_ID)
                .projectId(VALID_ID)
                .userId(VALID_ID)
                .build();
    }

    @Test
    void testOnMessageValid() throws IOException {
        //Act
        Mockito.when(objectMapper.readValue(message.getBody(), TeamEvent.class)).thenReturn(event);
        listener.onMessage(message, new byte[]{});
        //Assert
        Mockito.verify(eventHandlers).forEach(Mockito.any());
    }

    @Test
    void testOnMessageInvalid() throws IOException {
        //Act
        Mockito.when(objectMapper.readValue(message.getBody(), TeamEvent.class))
                .thenThrow(new RuntimeException(MESSAGE_ERROR));
        //Assert
        assertEquals(MESSAGE_ERROR,
                assertThrows(RuntimeException.class,
                        () -> listener.onMessage(message, new byte[]{})).getMessage());
    }
}