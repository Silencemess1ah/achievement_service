package faang.school.achievement.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AchievementEvent {

    private Long id;

    private String title;

    private Long userId;

    private LocalDateTime createdAt;
}
