package faang.school.achievement.event;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FollowerEvent {
    private Long userId;
    private LocalDateTime eventTime;
}
