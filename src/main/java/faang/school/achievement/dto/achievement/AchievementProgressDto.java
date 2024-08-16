package faang.school.achievement.dto.achievement;

import faang.school.achievement.model.Rarity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AchievementProgressDto {
    private long id;
    private long userId;
    private long currentPoints;
    private long achievementId;
    private String achievementTitle;
    private String achievementDescription;
    private Rarity achievementRarity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
