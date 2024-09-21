package faang.school.achievement.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FollowerEvent implements EventInt {
    private long userId;
    private long followerId;

    @Override
    public Long getUserId() {
        return userId;
    }
}
