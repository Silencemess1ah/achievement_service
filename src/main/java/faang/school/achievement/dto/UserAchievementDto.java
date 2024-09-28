package faang.school.achievement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAchievementDto {

    private Long id;
    private AchievementDto achievement;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
