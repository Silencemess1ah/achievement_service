package faang.school.achievement.dto;

import faang.school.achievement.model.Rarity;
import lombok.*;

/**
 * @author Evgenii Malkov
 */
@NoArgsConstructor
@Getter
@Setter
public class AchievementDto {
    private long id;
    private String title;
    private String description;
    private Rarity rarity;
}
