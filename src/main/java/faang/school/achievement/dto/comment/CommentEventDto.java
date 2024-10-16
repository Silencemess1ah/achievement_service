package faang.school.achievement.dto.comment;

import faang.school.achievement.dto.EventDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentEventDto extends EventDto {
    private Long commentAuthorId;
    private Long postId;
    private Long commentId;
    private String commentText;
}
