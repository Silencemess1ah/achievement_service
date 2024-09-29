package faang.school.achievement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Evgenii Malkov
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AchievementProgressDto {
    private long id;
    private AchievementDto achievement;
    private long userId;
    private long currentPoints;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long version;
    private AchievementDto achievementDto;
}
