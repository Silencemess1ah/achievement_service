package faang.school.achievement.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentAchievementEvent {
    private long id;
    private String content;
    private long authorId;
    private long postId;
}
