package faang.school.achievement.dto.achievement;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AchievementProgressDto {
    private long id;
    private long achievementId;
    private long userId;
    private long currentPoints;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long version;
}
