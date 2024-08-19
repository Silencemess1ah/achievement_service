package faang.school.achievement.dto;

import lombok.Builder;

@Builder
public record UserAchievementDto(
    Long id,
    long achievementId,
    long userId
) {
}
