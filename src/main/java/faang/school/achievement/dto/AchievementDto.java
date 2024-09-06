package faang.school.achievement.dto;

import faang.school.achievement.model.Rarity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Evgenii Malkov
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AchievementDto {

    private Long id;
    private String title;
    private String description;
    private Rarity rarity;
    private Long points;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
