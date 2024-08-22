package faang.school.achievement.messaging.handler.like;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.like.LikeEvent;
import faang.school.achievement.messaging.handler.EventHandler;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public abstract class LikeEventHandler implements EventHandler<LikeEvent> {
    private final AchievementService achievementService;
    private final AchievementCache achievementCache;
    private final String achievementTitle;

    @Async("achievementHandlerTaskExecutor")
    public void processEvent(LikeEvent event) {
        Achievement achievement = achievementCache.getAchievement(achievementTitle);
        long userId = event.getAuthorId();
        long achievementId = achievement.getId();

        if (!achievementService.hasAchievement(userId, achievementId)) {
            achievementService.createProgressIfNecessary(userId, achievementId);
            AchievementProgress achievementProgress = achievementService.getProgress(userId, achievementId);
            achievementProgress.increment();
            achievementService.saveAchievementProgress(achievementProgress);

            if (achievementProgress.getCurrentPoints() >= achievement.getPoints()) {
                achievementService.giveAchievement(userId, achievement);
            }
        }
    }
}
