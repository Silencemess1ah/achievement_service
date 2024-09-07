package faang.school.achievement.service.eventhandler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.event.Event;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@RequiredArgsConstructor
@Slf4j
@Transactional()
public abstract class AbstractEventHandler<T extends Event> implements EventHandler<T> {

    private final String achievementTitle;

    private final AchievementCache achievementCache;

    private final AchievementService achievementService;

    @Async("achievementsPool")
    @Override
    public void handle(T event) {
        Achievement achievement = achievementCache.get(achievementTitle);

        long userId = event.getUserId();
        long achievementId = achievement.getId();

        if (achievementService.userHasAchievement(userId, achievementId)) {
            log.info("User {} has achievement {}", userId, achievementId);
            return;
        }
        achievementService.createProgressIfNecessary(userId, achievementId);
        AchievementProgress progress
                = achievementService.getProgress(userId, achievementId);
        log.info("Got progress for achievement {} for User {} has ", achievementId, userId);
        incrementCurrentPoints(progress);
        if (progress.getCurrentPoints() >= achievement.getPoints()) {
            achievementService.giveAchievement(userId, achievement);
            log.info("Gave achievement {} to user {}", achievementId, userId);
        }
    }

    private void incrementCurrentPoints(AchievementProgress progress) {
        achievementService.incrementCurrentPointsForUser(progress);
    }
}
