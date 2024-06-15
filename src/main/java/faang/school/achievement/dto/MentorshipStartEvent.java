package faang.school.achievement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MentorshipStartEvent implements Event {
    private Long mentorId;
    private Long menteeId;

    @Override
    public long getAchievementHolderId() {
        return mentorId;
    }
}
