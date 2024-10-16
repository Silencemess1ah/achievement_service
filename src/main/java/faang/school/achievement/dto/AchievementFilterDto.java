package faang.school.achievement.dto;

import faang.school.achievement.model.Rarity;
import lombok.Data;

@Data
public class AchievementFilterDto {
    private String title;
    private String description;
    private Rarity rarity;
}
