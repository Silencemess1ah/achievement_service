package faang.school.achievement.dto;

import faang.school.achievement.model.Rarity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AchievementFilterDto {
    private String title;
    private String description;
    private Rarity rarity;
}
