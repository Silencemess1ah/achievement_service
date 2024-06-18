package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.SkillAcquiredEvent;
import faang.school.achievement.handlers.EventHandler;
import faang.school.achievement.handlers.WhoeverAchievementHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.Message;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SkillEventListenerTest {

    private static final long ACTOR_ID = 1L;
    private static final long RECEIVER_ID = 2L;
    private static final long SKILL_ID = 3L;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private List<EventHandler> handlers;

    @InjectMocks
    private SkillEventListener skillEventListener;

    private WhoeverAchievementHandler handler;
    private SkillAcquiredEvent messageEvent;

    @BeforeEach
    public void init() {
        messageEvent = new SkillAcquiredEvent(ACTOR_ID, RECEIVER_ID, SKILL_ID);
        handler = mock(WhoeverAchievementHandler.class);
    }

    @Test
    public void whenEventHandle() throws IOException {
        Message message = mock(Message.class);
        byte[] pattern = new byte[]{};
        String body = "{\"actorId\":1,\"receiverId\":2,\"requestId\":3}";

        when(message.getBody()).thenReturn(body.getBytes());
        when(objectMapper.readValue(body.getBytes(), SkillAcquiredEvent.class)).thenReturn(messageEvent);

        skillEventListener.onMessage(message, pattern);
    }
}