package faang.school.achievement.processor;

import faang.school.achievement.event.AchievementEvent;
import faang.school.achievement.event.AchievementPublisher;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public abstract class BaseAchievementProcessor implements AchievementProcessor {

    private final AchievementService achievementService;
    private final AchievementPublisher achievementPublisher;
    private final String achievementTitle;

    public BaseAchievementProcessor(AchievementService achievementService, AchievementPublisher achievementPublisher, String achievementTitle) {
        this.achievementService = achievementService;
        this.achievementPublisher = achievementPublisher;
        this.achievementTitle = achievementTitle;
    }

    @Async
    @Retryable
    @Transactional
    public void processAchievement(Long userId) {
        Achievement achievement = achievementService.findByTitle(achievementTitle);
        if (isAchievementNotAlreadyAwarded(userId, achievement)) {
            AchievementProgress updatedAchievementProgress = this.updateAchievementProgress(userId, achievement.getId());
            if (hasUserAchievedGoals(updatedAchievementProgress, achievement)) {
                awardAchievement(userId, achievement);
            }
        }
    }

    private static boolean hasUserAchievedGoals(AchievementProgress updatedAchievementProgress, Achievement achievement) {
        return updatedAchievementProgress.getCurrentPoints() >= achievement.getPoints();
    }

    private boolean isAchievementNotAlreadyAwarded(Long userId, Achievement achievement) {
        return !achievementService.hasAchievement(userId, achievement.getId());
    }

    private AchievementProgress updateAchievementProgress(Long userId, Long achievementId) {
        achievementService.createProgressIfNecessary(userId, achievementId);
        AchievementProgress achievementProgress = achievementService.getProgress(userId, achievementId);
        achievementProgress.increment();
        achievementService.saveAndFlush(achievementProgress);
        log.info("User {} has updated their points to {} for achievement '{}'.", userId,
                achievementProgress.getCurrentPoints(), achievementId);
        return achievementProgress;
    }

    private void awardAchievement(Long userId, Achievement achievement) {
        achievementService.giveAchievement(userId, achievement);
        log.info("User {} has received achievement '{}'.", userId, achievement.getTitle());
        achievementPublisher.publish(new AchievementEvent(userId, achievement.getId()));
    }

    @Recover
    public void recover(Exception e, Long userId, String achievementTitle) {
        log.error("Failed to process achievement '{}' for user {}: {}",
                achievementTitle, userId, e.getMessage(), e);
    }
}
