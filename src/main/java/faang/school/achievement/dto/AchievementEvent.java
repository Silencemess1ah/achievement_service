package faang.school.achievement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AchievementEvent {
    private Long id;
    private String title;
}
