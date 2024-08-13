package faang.school.achievement.dto;

import faang.school.achievement.model.Rarity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AchievementDto {
    @NotNull
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String description;
    private Rarity rarity;
    private List<UserAchievementDto> userAchievementDtos;
    private List<AchievementProgressDto> achievementProgressDtos;
    private Long points;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
