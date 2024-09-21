package faang.school.achievement.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamEvent implements EventInt {
    private Long userId;
    private Long projectId;
    private Long teamId;

    @Override
    public Long getUserId() {
        return userId;
    }
}