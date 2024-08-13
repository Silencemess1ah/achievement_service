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
public class UserAchievementDto {
    private Long id;
    private Long achievementId;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
