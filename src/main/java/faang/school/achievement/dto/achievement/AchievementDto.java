package faang.school.achievement.dto.achievement;

import faang.school.achievement.model.Rarity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AchievementDto {
    private Long id;
    private String title;
    private String description;
    private Rarity rarity;
    private long points;
}
