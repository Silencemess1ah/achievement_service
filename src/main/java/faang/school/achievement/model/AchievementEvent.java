package faang.school.achievement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class AchievementEvent {

    private Long id;

    private String title;

    private Long userId;

    private LocalDateTime createdAt;
}
