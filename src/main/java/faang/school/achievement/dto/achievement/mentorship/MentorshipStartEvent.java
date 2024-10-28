package faang.school.achievement.dto.achievement.mentorship;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import faang.school.achievement.dto.achievement.AbstractEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MentorshipStartEvent extends AbstractEvent {

    private Long mentorId;
    private Long menteeId;

    @Override
    @JsonProperty("userId")
    public void setUserId(long userId) {
        this.userId = userId;
    }
}
