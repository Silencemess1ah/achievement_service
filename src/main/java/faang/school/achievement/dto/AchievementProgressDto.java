package faang.school.achievement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Evgenii Malkov
 */
@NoArgsConstructor
@Getter
@Setter
public class AchievementProgressDto {
    private long id;
    private AchievementDto achievement;
    private long userId;
    private long currentPoints;
}
