package faang.school.achievement.eventhandler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.Event;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractEventHandler<T extends Event> implements EventHandler<T> {

    private final AchievementCache achievementCache;
    private final AchievementService achievementService;

    @Override
    @Async
    public void handleEvent(T event) {
        Achievement achievement = achievementCache.getAchievement(getAchievementName());

        long userId = event.getAchievementHolderId();
        long achievementId = achievement.getId();

        if (achievementService.hasAchievement(userId, achievementId)) {
            log.info("User has already received achievement: {}", getAchievementName());
            return;
        }

        achievementService.createProgressIfNecessary(userId, achievementId);
        AchievementProgress progress = achievementService.getProgress(userId, achievementId);
        progress.increment();

        if (progress.getCurrentPoints() >= achievement.getPoints()) {
            achievementService.giveAchievement(achievement, userId);
        }
    }

    protected abstract String getAchievementName();
}
