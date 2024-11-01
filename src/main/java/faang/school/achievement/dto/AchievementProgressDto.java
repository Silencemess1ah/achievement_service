package faang.school.achievement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AchievementProgressDto {
    private Long id;
    private Long userId;
    private Long currentPoints;
    private AchievementDto achievement;
}
