package faang.school.achievement.event;

import faang.school.achievement.model.Achievement;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AchievementRecievedEvent {
    private Achievement achievement;
}
