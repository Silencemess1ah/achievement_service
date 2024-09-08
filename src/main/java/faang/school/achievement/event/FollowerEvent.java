package faang.school.achievement.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class FollowerEvent {

    private long userId;
    private long followUserId;
    private long projectId;
    private Instant subscriptionTime;
}
