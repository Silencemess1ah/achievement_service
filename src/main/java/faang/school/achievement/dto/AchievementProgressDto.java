package faang.school.achievement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AchievementProgressDto {

    private long id;

    private long achievementId;

    private long userId;

    private long currentPoints;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
