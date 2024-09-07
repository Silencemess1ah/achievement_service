package faang.school.achievement.events;

import faang.school.achievement.model.event.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentEvent extends Event {
    private long postId;
    private long commentId;
    private LocalDateTime sendAt;
}
