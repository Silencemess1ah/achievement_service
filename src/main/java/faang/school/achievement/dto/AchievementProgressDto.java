package faang.school.achievement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AchievementProgressDto {
    private long id;
    private AchievementDto achievement;
    private long userId;
    private long currentPoints;
}
