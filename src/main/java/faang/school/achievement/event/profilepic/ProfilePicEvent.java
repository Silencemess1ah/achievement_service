package faang.school.achievement.event.profilepic;

import faang.school.achievement.event.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfilePicEvent implements Event {
    private UUID eventId;
    private Long userId;
    private String avatarUrl;

    @Override
    public long getUserId(){
        return userId;
    }
}

