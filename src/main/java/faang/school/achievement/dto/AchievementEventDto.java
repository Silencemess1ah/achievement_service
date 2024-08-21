package faang.school.achievement.dto;

import lombok.Builder;

@Builder
public record AchievementEventDto(
    long userId,
    long achievementId
) {
}
