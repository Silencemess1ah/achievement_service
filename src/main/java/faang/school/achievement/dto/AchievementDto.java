package faang.school.achievement.dto;

import faang.school.achievement.model.Rarity;
import lombok.Builder;

@Builder
public record AchievementDto(
    Long id,
    String title,
    String description,
    Rarity rarity,
    long points
) {
}
