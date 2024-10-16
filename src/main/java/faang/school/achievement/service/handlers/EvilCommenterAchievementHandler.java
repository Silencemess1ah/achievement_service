package faang.school.achievement.service.handlers;

import faang.school.achievement.dto.comment.CommentEventDto;
import org.springframework.stereotype.Component;

@Component
public class EvilCommenterAchievementHandler implements EventHandler<CommentEventDto> {
    @Override
    public void handle(CommentEventDto event) {

    }
}
