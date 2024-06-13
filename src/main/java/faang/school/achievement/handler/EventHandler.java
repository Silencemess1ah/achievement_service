package faang.school.achievement.handler;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;

import java.util.Optional;

public interface EventHandler<T> {
    boolean canHandle(T event);
    void handle(T event);

    default Optional<UserAchievement> generateUserAchievement(AchievementProgress progress, Achievement achievement) {
        if (isEnoughPoints(progress, achievement)) {
            UserAchievement userAchievement = UserAchievement.builder()
                    .achievement(achievement)
                    .userId(progress.getUserId())
                    .build();
            return Optional.of(userAchievement);
        }
        return Optional.empty();
    }

    private boolean isEnoughPoints(AchievementProgress progress, Achievement achievement) {
        return progress.getCurrentPoints() >= achievement.getPoints();
    }
}