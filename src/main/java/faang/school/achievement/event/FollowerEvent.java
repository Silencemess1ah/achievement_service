package faang.school.achievement.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class FollowerEvent extends Event {
    private Long userId;

    public FollowerEvent(LocalDateTime eventTime, Long userId) {
        super(eventTime);
        this.userId = userId;
    }
}
