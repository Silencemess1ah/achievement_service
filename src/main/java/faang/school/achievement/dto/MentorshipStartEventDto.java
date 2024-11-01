package faang.school.achievement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record MentorshipStartEventDto(long mentorId,
                                      long menteeId) {
}
