package faang.school.achievement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public abstract class Event {
    protected LocalDateTime eventTime;
}
