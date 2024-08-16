package faang.school.achievement.dto.achievement;

import faang.school.achievement.model.Rarity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AchievementDto {
    private long id;
    private String title;
    private String description;
    private Rarity rarity;
    private long points;
}
