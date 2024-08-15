package faang.school.achievement.dto;

import faang.school.achievement.model.Rarity;
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

    private long id;

    private String title;

    private String description;

    private Rarity rarity;

    private List<Long> userAchievementIds;

    private List<Long> progressIds;

    private long points;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
