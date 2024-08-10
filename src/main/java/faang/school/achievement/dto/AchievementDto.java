package faang.school.achievement.dto;

import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.model.UserAchievement;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class AchievementDto {
    private long id;
    private String title;
    private String description;
    private Rarity rarity;
    private List<UserAchievementDto> userAchievements;
    private List<AchievementProgressDto> progresses;
    private long points;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
