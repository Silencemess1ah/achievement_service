package faang.school.achievement.redis.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.model.event.ProfilePicEvent;
import faang.school.achievement.service.eventhandler.AbstractEventHandler;
import faang.school.achievement.service.eventhandler.profilepic.HandsomeAchievementHandler;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.redis.connection.DefaultMessage;
import org.springframework.data.redis.connection.Message;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

class ProfilePicEventListenerTest {

    private final HandsomeAchievementHandler handsomeAchievementHandler
            = Mockito.mock(HandsomeAchievementHandler.class);
    private final List<AbstractEventHandler<ProfilePicEvent>> handlers
            = List.of(handsomeAchievementHandler);
    private final ObjectMapper objectMapper
            = Mockito.spy(new ObjectMapper());

    private final ProfilePicEventListener profilePicEventListener
            = new ProfilePicEventListener(objectMapper, handlers);

    @Test
    void testOnMessage() throws IOException {
        ProfilePicEvent profilePicEvent = new ProfilePicEvent();
        profilePicEvent.setUserId(111);

        byte[] channel = "channel".getBytes(StandardCharsets.UTF_8);
        byte[] body = objectMapper.writeValueAsBytes(profilePicEvent);
        Message message = new DefaultMessage(channel, body);

        profilePicEventListener.onMessage(message, channel);

        Mockito.verify(objectMapper, Mockito.times(1)).readValue(body, ProfilePicEvent.class);
        Mockito.verify(handsomeAchievementHandler, Mockito.times(1)).handle(profilePicEvent);
    }

}