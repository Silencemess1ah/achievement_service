package faang.school.achievement.event.post;

import faang.school.achievement.event.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostEvent implements Event {
    private long id;
    private long authorId;

    @Override
    public long getUserId() {
        return authorId;
    }
}
