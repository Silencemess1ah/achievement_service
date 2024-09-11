package faang.school.achievement.event;

import lombok.Data;

@Data
public class MentorshipStartEvent {
    private Long id;
    private Long requesterId;
    private Long receiverId;
}
