package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public abstract class AbstractAchievementHandler<T> {

    private final AchievementService achievementService;
    private final AchievementCache achievementCache;

    @Transactional
    @Async("achievementHandlerTaskExecutor")
    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 100))
    protected void handleAchievement(Long userId, String achievementTitle) {
        AchievementDto achievement = achievementCache.get(achievementTitle);

        if (!achievementService.hasAchievement(userId, achievement.getId())) {
            achievementService.createProgressIfNecessary(userId, achievement.getId());
            AchievementProgressDto achievementProgress = achievementService.getAchievementProgress(userId, achievement.getId());
            achievementProgress.increment();

            achievementService.saveAchievementProgress(achievementProgress);

            tryToGetAchievement(achievement, achievementProgress);
        }
    }

    public void tryToGetAchievement(AchievementDto achievement, AchievementProgressDto achievementProgress) {
        if (achievementProgress.getCurrentPoints() >= achievement.getPoints()) {
            achievementService.giveAchievement(achievement, achievementProgress.getUserId());
        }
    }
}
