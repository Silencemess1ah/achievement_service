package faang.school.achievement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class AchievementEvent implements Serializable {
    private long userId;
    private long achievementId;
    private String achievementTitle;
}
