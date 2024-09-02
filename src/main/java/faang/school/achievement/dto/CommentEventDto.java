package faang.school.achievement.dto;

import lombok.Data;

@Data
public class CommentEventDto {
    private long commentAuthorId;
    private long postAuthorId;
    private long commentId;
    private long postId;
    private String content;
}
