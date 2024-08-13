package faang.school.achievement.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserAchievementDto {
    private long id;
    private long achievementId;
    private long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
