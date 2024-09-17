package faang.school.achievement.event.post;

import faang.school.achievement.event.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostEvent implements Event {
    private long authorId;
    private long postId;
}
