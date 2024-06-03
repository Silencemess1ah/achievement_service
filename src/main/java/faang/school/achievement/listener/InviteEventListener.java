package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.InviteSentEvent;
import faang.school.achievement.exception.EventException;
import faang.school.achievement.handler.OrganizerAchievementHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InviteEventListener implements MessageListener {

    private final ObjectMapper mapper;
    private final OrganizerAchievementHandler organizerAchievementHandler;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            InviteSentEvent event = mapper.convertValue(message.getBody(), InviteSentEvent.class);
            organizerAchievementHandler.handle(event);
            log.info("InviteEvent was handled properly - {}", event);
        } catch (IllegalArgumentException e) {
            throw new EventException("Cannot convert message body to InviteSentEvent - " + e.getMessage());
        }
    }
}
