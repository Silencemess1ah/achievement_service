package faang.school.achievement.dto;

import faang.school.achievement.model.Achievement;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AchievementProgressDto {
    private long id;
    private long achievementId;
    private long userId;
    private long currentPoints;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long version;

}
