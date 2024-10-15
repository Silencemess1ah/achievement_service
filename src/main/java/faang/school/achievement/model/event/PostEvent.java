package faang.school.achievement.model.event;

import lombok.Builder;

@Builder
public record PostEvent(
        long authorId,
        long postId
) {
}
