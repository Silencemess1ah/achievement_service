package faang.school.achievement.service.user_achievement;

import faang.school.achievement.dto.achievement.UserAchievementDto;
import faang.school.achievement.model.Achievement;

import java.util.List;

public interface UserAchievementService {

    List<UserAchievementDto> getAchievementsByUserId(long userId);

    void giveAchievement(long userId, Achievement achievement);

    boolean hasAchievement(long userId, long achievementId);
}
