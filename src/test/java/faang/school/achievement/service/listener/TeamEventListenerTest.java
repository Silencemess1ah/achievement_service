package faang.school.achievement.service.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.event.TeamEvent;
import faang.school.achievement.service.handler.eventHandler.AbstractEventHandler;
import faang.school.achievement.service.handler.eventHandler.teamEvent.ManagerAchievementHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.connection.Message;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TeamEventListenerTest {

    private static final String MESSAGE_ERROR = "ReadValue exception";
    private static final String channelName = "comment_channel";
    private static final long VALID_ID = 1L;
    private final String STRING = "{\"teamId\":1,\"userId\":2,\"projectId\":3}";
    private TeamEvent event;
    private Message message;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private ManagerAchievementHandler handler;
    @Mock
    private MessageSource messageSource;
    private final Class<TeamEvent> clazz = TeamEvent.class;
    private TeamEventListener listener;

    @BeforeEach
    void setUp() {
        //Arrange
        List<AbstractEventHandler<TeamEvent>> eventHandlers = List.of(handler);
        listener = new TeamEventListener(objectMapper, eventHandlers, messageSource, clazz, channelName);

        message = new Message() {
            @Override
            public byte[] getBody() {
                return STRING.getBytes();
            }

            @Override
            public byte[] getChannel() {
                return new byte[0];
            }
        };
        event = TeamEvent.builder()
                .teamId(VALID_ID)
                .userId(VALID_ID)
                .projectId(VALID_ID)
                .build();
    }

    @Test
    void testOnMessageValid() throws IOException {
        //Act
        Mockito.when(objectMapper.readValue(message.getBody(), TeamEvent.class)).thenReturn(event);
        listener.onMessage(message, new byte[]{});
        //Assert
        Mockito.verify(handler).process(Mockito.any());
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