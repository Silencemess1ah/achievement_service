package faang.school.achievement.event.like;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import faang.school.achievement.event.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LikeEvent implements Event {
    private UUID eventId;
    private long authorId;
    private long postId;
    private long likeId;

    @Override
    public long getUserId() {
        return authorId;
    }
}
