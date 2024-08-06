package faang.school.achievement.dto;

import faang.school.achievement.model.Rarity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AchievementFilterDto {
    private String titlePrefix;
    private String descriptionPrefix;
    private Rarity rarity;
}
