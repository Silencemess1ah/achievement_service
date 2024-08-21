package faang.school.achievement.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

//@RequiredArgsConstructor
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentEvent {
    private  long authorId;
    private  long postId;
    private  long commentId;
    private  LocalDateTime sendAt;
//    private final String content;
}
