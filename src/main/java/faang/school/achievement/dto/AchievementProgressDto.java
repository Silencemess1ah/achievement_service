package faang.school.achievement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AchievementProgressDto {

    private Long id;
    private AchievementDto achievementDto;
    private Long userId;
    private Long currentPoints;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long version;
}
