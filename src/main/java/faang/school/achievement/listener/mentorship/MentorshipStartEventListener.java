package faang.school.achievement.listener.mentorship;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.mentorship.MentorshipStartEvent;
import faang.school.achievement.handler.mentorship.SenseiAchievementHandler;
import faang.school.achievement.listener.AbstractEventListener;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MentorshipStartEventListener extends AbstractEventListener<MentorshipStartEvent> {

    private final SenseiAchievementHandler senseiAchievementHandler;

    public MentorshipStartEventListener(ObjectMapper objectMapper, SenseiAchievementHandler senseiAchievementHandler) {
        super(objectMapper);
        this.senseiAchievementHandler = senseiAchievementHandler;
    }

    @Override
    public void onMessage(@NonNull Message message, byte[] pattern) {
        handleEvent(message, MentorshipStartEvent.class, senseiAchievementHandler::handle);
    }
}
