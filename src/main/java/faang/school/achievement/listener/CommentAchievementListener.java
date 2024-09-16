package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.handler.AbstractAchievementHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentAchievementListener extends AbstractEventListener<CommentAchievementListener> {
    public CommentAchievementListener(ObjectMapper objectMapper,
                                      List<AbstractAchievementHandler<CommentAchievementListener>> eventHandlers) {
        super(objectMapper, eventHandlers, CommentAchievementListener.class);
    }
}
