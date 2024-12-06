package faang.school.achievement.dto.goal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import faang.school.achievement.dto.achievement.AbstractEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoalSetEventDto extends AbstractEvent {
    private long goalId;

    @Override
    @JsonProperty("userId")
    public void setUserId(long userId) {
        this.userId = userId;
    }
}
