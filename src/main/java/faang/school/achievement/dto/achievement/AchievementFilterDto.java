package faang.school.achievement.dto.achievement;

import faang.school.achievement.model.Rarity;
import lombok.Data;

@Data
public class AchievementFilterDto {
    String title;
    String description;
    Rarity rarity;
}
