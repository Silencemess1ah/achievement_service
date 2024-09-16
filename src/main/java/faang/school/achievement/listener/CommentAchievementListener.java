package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.CommentAchievementEvent;
import faang.school.achievement.handler.AbstractAchievementHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentAchievementListener extends AbstractEventListener<CommentAchievementEvent> {
    public CommentAchievementListener(ObjectMapper objectMapper,
                                      List<AbstractAchievementHandler<CommentAchievementEvent>> eventHandlers) {
        super(objectMapper, eventHandlers, CommentAchievementEvent.class);
    }
}
