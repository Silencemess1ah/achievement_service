package faang.school.achievement.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public abstract class Event {
    protected LocalDateTime eventTime;
}
