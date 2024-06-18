package faang.school.achievement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SkillAcquiredEvent extends MessageEvent {

    private long skillId;

    public SkillAcquiredEvent(long actorId, long receiverId, long skillId) {
        super(actorId, receiverId);
        this.skillId = skillId;
    }
}
