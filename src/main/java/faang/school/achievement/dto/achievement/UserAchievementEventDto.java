package faang.school.achievement.dto.achievement;

import faang.school.achievement.model.Rarity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public record UserAchievementEventDto(long userId,
                                      long achievementId,
                                      Rarity rarity,
                                      String title,
                                      LocalDateTime createdAt) {

}
