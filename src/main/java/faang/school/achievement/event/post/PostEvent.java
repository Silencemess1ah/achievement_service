package faang.school.achievement.event.post;

import faang.school.achievement.event.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostEvent implements Event {
    private long authorId;
    private long postId;
}
