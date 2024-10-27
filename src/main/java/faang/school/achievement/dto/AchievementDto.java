package faang.school.achievement.dto;

import faang.school.achievement.model.AchievementTitle;
import faang.school.achievement.model.Rarity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AchievementDto {
    private Long id;
    private AchievementTitle title;
    private String description;
    private Rarity rarity;
    private Long points;
}
