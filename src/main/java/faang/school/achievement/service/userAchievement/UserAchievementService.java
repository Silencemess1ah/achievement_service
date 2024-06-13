package faang.school.achievement.service.userAchievement;

import faang.school.achievement.model.UserAchievement;

public interface UserAchievementService {
    boolean hasAchievement(long userId, long achievementId);
    void assignAchievement(UserAchievement userAchievement);
}
