package faang.school.achievement.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AchievementProgressDto {

    private Long id;
    private Long achievementId;
    private Long userId;
    private Long currentPoints;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long version;
    private AchievementDto achievementDto;
}
