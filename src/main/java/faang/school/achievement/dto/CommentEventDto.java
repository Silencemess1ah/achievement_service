package faang.school.achievement.dto;

import lombok.Builder;

@Builder
public record CommentEventDto(
        Long authorId,
        Long postId,
        Long commentId,
        String content) {
}
