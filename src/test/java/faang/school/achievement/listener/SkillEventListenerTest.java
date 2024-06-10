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
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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
        messageEvent = SkillAcquiredEvent.builder()
                .actorId(ACTOR_ID)
                .receiverId(RECEIVER_ID)
                .skillId(SKILL_ID)
                .build();

        handler = mock(WhoeverAchievementHandler.class);
        when(handlers.stream()).thenAnswer(invocation -> Stream.of(handler));
    }

    @Test
    public void whenEventHandle() throws IOException {
        Message message = mock(Message.class);
        byte[] pattern = new byte[]{};
        String body = "{\"actorId\":1,\"receiverId\":2,\"requestId\":3}";
        String channel = "mentorship_accepted_channel";

        when(message.getBody()).thenReturn(body.getBytes());
        when(message.getChannel()).thenReturn(channel.getBytes());
        when(objectMapper.readValue(body.getBytes(), SkillAcquiredEvent.class)).thenReturn(messageEvent);

        skillEventListener.onMessage(message, pattern);

        verify(handlers).stream();
    }
}