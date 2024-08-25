package faang.school.achievement.event.like;

import faang.school.achievement.event.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeEvent implements Event {
    private long authorId;
    private long postId;
    private long likeId;

    @Override
    public long getUserId() {
        return authorId;
    }
}
