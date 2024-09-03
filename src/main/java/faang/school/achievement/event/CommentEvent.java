package faang.school.achievement.event;

import lombok.Data;

@Data
public class CommentEvent {
    private long commentAuthorId;
    private long postAuthorId;
    private long commentId;
    private long postId;
    private String content;
}
