package faang.school.achievement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MentorshipStartEvent implements Event {
    private Long mentorId;
    private Long menteeId;

    @Override
    public long getAchievementHolderId() {
        return mentorId;
    }
}
