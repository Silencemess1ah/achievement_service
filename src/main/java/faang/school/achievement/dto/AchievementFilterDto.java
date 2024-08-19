package faang.school.achievement.dto;

import faang.school.achievement.model.Rarity;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AchievementFilterDto {
    @Size(max = 255)
    private String titlePattern;

    @Size(max = 255)
    private String descriptionPattern;
    private Rarity rarityPattern;
}
