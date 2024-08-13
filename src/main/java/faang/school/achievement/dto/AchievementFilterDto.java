package faang.school.achievement.dto;

import faang.school.achievement.model.Rarity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Evgenii Malkov
 */
@NoArgsConstructor
@Getter
@Setter
public class AchievementFilterDto {
    private String title;
    private String description;
    private Rarity rarity;
}
