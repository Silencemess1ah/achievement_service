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
public class UserAchievementDto {

    private long id;
    private long userId;
    private long achievementId;
    private String achievementTitle;
    private String achievementDescription;
    private Rarity achievementRarity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
