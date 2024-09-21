package faang.school.achievement.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentEvent implements EventInt {
    private Long id;
    private String content;
    private Long authorId;
    private Long postId;

    @Override
    public Long getUserId() {
        return authorId;
    }
}
