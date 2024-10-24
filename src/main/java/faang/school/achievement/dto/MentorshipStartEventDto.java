package faang.school.achievement.dto;

import lombok.Builder;

@Builder
public record MentorshipStartEventDto(long mentorId,
                                      long menteeId) {
}
