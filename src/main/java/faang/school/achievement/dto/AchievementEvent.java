package faang.school.achievement.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AchievementEvent {
    private Long userId;
    private String title;
    private String description;
    private String rarity;
    private LocalDateTime createdAt;
}
