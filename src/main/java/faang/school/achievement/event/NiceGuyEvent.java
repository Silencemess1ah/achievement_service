package faang.school.achievement.event;

import faang.school.achievement.model.event.AuthorSearcher;
import lombok.Builder;

@Builder
public record NiceGuyEvent(
        Long authorId,
        Long receiverId,
        Long recommendationId
) implements AuthorSearcher {
    @Override
    public long getAuthorForAchievements() {
        return authorId;
    }
}
