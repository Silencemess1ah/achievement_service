package faang.school.achievement.events;

import faang.school.achievement.model.event.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentEvent extends Event {
    private long postId;
    private long commentId;
    private LocalDateTime sendAt;
}
