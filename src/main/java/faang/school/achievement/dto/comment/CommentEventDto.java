package faang.school.achievement.dto.comment;

import lombok.Data;

@Data
public class CommentEventDto {
    private Long commentAuthorId;
    private Long postId;
    private Long commentId;
    private String commentText;
}
