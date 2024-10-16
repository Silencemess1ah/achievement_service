package faang.school.achievement.handler.comment;

import faang.school.achievement.dto.comment.CommentEventDto;
import faang.school.achievement.handler.EventHandler;

public abstract class CommentEventHandler implements EventHandler {
    public abstract void verifyAchievement(CommentEventDto commentEventDto);
}
