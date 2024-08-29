package faang.school.achievement.dto;

import faang.school.achievement.model.Achievement;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AchievementProgressDto {

    private long id;

    private Achievement achievement;

    private long userId;

    private long currentPoints;
}
