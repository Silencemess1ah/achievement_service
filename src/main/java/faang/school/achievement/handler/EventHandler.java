package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;

@RequiredArgsConstructor
public abstract class EventHandler<T> {
    protected final AchievementCache achievementCache;
    protected final AchievementService achievementService;

    @Async(value = "threadPool")
    public void handleEvent(T event) {
        String achievementTitle = getAchievementTitle();
        Achievement achievement = achievementCache.getAchievementByTitle(achievementTitle)
                .orElseThrow(() -> new EntityNotFoundException("Achievement with title %s not found"
                        .formatted(achievementTitle)));
        long userId = getUserId(event);
        handleAchievement(userId, achievement);
    }

    protected void handleAchievement(long userId, Achievement achievement) {
        long achievementId = achievement.getId();
        if (!achievementService.hasAchievement(userId, achievementId)) {
            achievementService.createProgressIfNecessary(userId, achievementId);
            AchievementProgress achievementProgress = achievementService.getProgress(userId, achievementId);
            achievementProgress.increment();
            if (achievementProgress.getCurrentPoints()>= getPointsToEarnAchievement()) {
                achievementService.giveAchievement(userId, achievement);
            }
            achievementService.saveAchievementProgress(achievementProgress);
        }
    }

    protected abstract String getAchievementTitle();
    protected abstract long getUserId(T event);
    protected abstract long getPointsToEarnAchievement();
}
