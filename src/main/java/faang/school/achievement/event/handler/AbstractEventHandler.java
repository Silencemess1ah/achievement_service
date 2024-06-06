package faang.school.achievement.event.handler;

import faang.school.achievement.dto.Event;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.achievement.AchievementService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractEventHandler<T extends Event> implements EventHandler<T> {

    protected final AchievementService achievementService;
    protected final UserAchievementRepository userAchievementRepository;
    protected final AchievementProgressRepository achievementProgressRepository;

    protected abstract String getAchievementName();

    protected void giveAchievementIfEnoughScore(AchievementProgress progress, Achievement achievement) {
        if (progress.getCurrentPoints() >= achievement.getPoints()) {
            UserAchievement userAchievement = UserAchievement.builder()
                    .userId(progress.getUserId())
                    .achievement(achievement)
                    .build();
            achievementService.giveAchievement(userAchievement);
        }
    }

    @Override
    @Async
    @Retryable(value = {OptimisticLockingFailureException.class}, maxAttempts = 4, backoff = @Backoff(delay = 1000))
    public void handleEvent(T event) {
        Achievement achievement = null;
        try {
            achievement = achievementService.getAchievementByName(getAchievementName());
        } catch (EntityNotFoundException exception) {
            log.warn(exception.getMessage());
            return;
        }

        long userId = event.getAchievementHolderId();
        long achievementId = achievement.getId();

        if (achievementService.hasAchievement(userId, achievementId)) {
            log.info("User has already received achievement: {}", getAchievementName());
            return;
        }

        AchievementProgress progress = achievementService.getProgress(userId, achievement);
        achievementService.incrementAchievementProgress(progress);
        achievementService.updateAchievementProgress(progress);
        giveAchievementIfEnoughScore(progress, achievement);
    }
}
