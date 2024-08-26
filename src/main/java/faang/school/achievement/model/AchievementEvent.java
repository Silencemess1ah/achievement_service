package faang.school.achievement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AchievementEvent {
    private long id;
    private String title;
    private String description;
    private String rarity;
    private long points;
}
