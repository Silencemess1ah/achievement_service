package faang.school.achievement.listener.goal;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.goal.GoalSetEventDto;
import faang.school.achievement.handler.goal.GoalSetCollectorAchievementHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.Message;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GoalEventListenerTest {

    @InjectMocks
    private GoalSetEventListener goalEventListener;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private List<GoalSetCollectorAchievementHandler> handlers;

    @Mock
    private Message message;

    private GoalSetEventDto goalSetEventDto;

    @BeforeEach
    void setUp() {
        goalSetEventDto = GoalSetEventDto.builder().goalId(1L).build();
    }

    @Test
    @DisplayName("Handle goal event and pass to all handlers")
    void whenJsonPassedThenPassItToAllHandlers() throws IOException {
        when(message.getBody()).thenReturn(new byte[0]);
        when(objectMapper.readValue(any(byte[].class), eq(GoalSetEventDto.class))).thenReturn(goalSetEventDto);

        goalEventListener.onMessage(message, null);

        verify(objectMapper).readValue(any(byte[].class), eq(GoalSetEventDto.class));
        handlers.forEach(handler -> verify(handler).handle(goalSetEventDto));
    }

    @Test
    @DisplayName("Throw RuntimeException if IOException occurs during deserialization")
    void whenIoeExceptionOccursThenThrowsException() throws IOException {
        when(message.getBody()).thenReturn(new byte[0]);
        when(objectMapper.readValue(any(byte[].class), eq(GoalSetEventDto.class))).thenThrow(new IOException());

        assertThrows(RuntimeException.class, () -> goalEventListener.onMessage(message, null));
    }
}
