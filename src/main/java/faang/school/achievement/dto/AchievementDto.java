package faang.school.achievement.dto;

import lombok.Data;

@Data
public class AchievementDto {
    private Long id;
    private String title;
    private Enum rarity;
}
