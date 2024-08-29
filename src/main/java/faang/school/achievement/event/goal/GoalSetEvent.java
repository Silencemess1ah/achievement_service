package faang.school.achievement.event.goal;

import lombok.Data;

@Data
public class GoalSetEvent {
    private Long userId;
    private Long goalId;
}
