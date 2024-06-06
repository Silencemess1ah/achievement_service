package faang.school.achievement.service.achievement;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;

public interface AchievementService {

    void doWork(long userId, long achievementId);

    boolean hasAchievement(long userId, long achievementId);

    AchievementProgress getProgress(long userId, Achievement achievement);


    void giveAchievement(UserAchievement userAchievement);

    Achievement getAchievementByName(String name);

    Achievement getAchievementById(long id);

    void incrementAchievementProgress(AchievementProgress achievementProgress);

    void updateAchievementProgress(AchievementProgress achievementProgress);

    AchievementProgress saveProgressWithUserIdAndAchievement(long userId, Achievement achievement);

    AchievementProgress createProgressIfNecessary(long userId, Achievement achievement);
}