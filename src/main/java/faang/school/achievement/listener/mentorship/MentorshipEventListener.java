package faang.school.achievement.listener.mentorship;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.dto.mentorship.MentorshipStartEvent;
import faang.school.achievement.listener.AbstractEventListener;
import faang.school.achievement.service.achievement.AchievementService;
import lombok.NonNull;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

@Component
public class MentorshipEventListener extends AbstractEventListener<MentorshipStartEvent> {

    private final AchievementService achievementService;

    public MentorshipEventListener(ObjectMapper objectMapper, AchievementService achievementService) {
        super(objectMapper);
        this.achievementService = achievementService;
    }

    @Override
    public void onMessage(@NonNull Message message, byte[] pattern) {
        handleEvent(message, MentorshipStartEvent.class, event -> achievementService.doWork(event.getMentorId(), event.getAchievementHolderId()));
    }
}
