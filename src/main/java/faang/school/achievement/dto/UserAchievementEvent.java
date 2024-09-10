package faang.school.achievement.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserAchievementEvent {
    private long userId;
    private long achievementId;
    private String achievementTitle;
    private LocalDateTime createdAt;
}
