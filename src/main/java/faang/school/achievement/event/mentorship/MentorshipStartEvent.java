package faang.school.achievement.event.mentorship;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import faang.school.achievement.event.Event;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class MentorshipStartEvent implements Event {
    private long mentorId;
    private long menteeId;
}
