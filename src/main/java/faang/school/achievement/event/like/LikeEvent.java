package faang.school.achievement.event.like;

import faang.school.achievement.event.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeEvent implements Event {
    private UUID eventId;
    private long authorId;
    private long postId;
    private long likeId;
    private LocalDateTime timeStamp;

    @Override
    public long getUserId() {
        return authorId;
    }
}
