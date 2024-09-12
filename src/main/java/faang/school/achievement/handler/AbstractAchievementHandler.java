package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;

import java.awt.print.PrinterException;

@RequiredArgsConstructor
@Slf4j
public class AbstractAchievementHandler<T> implements EventHandler<T> {
    protected final AchievementService achievementService;
    protected final AchievementCache achievementCache;
    private final String achievementTitle;
    private final AchievementProgressRepository achievementProgressRepository;
    private final AchievementMapper achievementMapper;

    @Async("achievementHandlerTaskExecutor")
    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 10), retryFor = PrinterException.class)
    public void handle(long userId) {
        Achievement achievement = achievementMapper.toEntity(achievementCache.getByTitle(achievementTitle));
        log.info("Achievement found {}, name{}", achievement.getId(), achievement.getTitle());
        if (!achievementService.hasAchievement(userId, achievement)) {
            achievementService.createProgressIfNecessary(userId, achievement.getId());
            AchievementProgress achievementProgress = achievementService.getAchievementProgress(userId, achievement);
            achievementProgress.increment();
            achievementProgressRepository.save(achievementProgress);

            hasAchievementCompleted(achievement, achievementProgress);
        }
    }

    private void hasAchievementCompleted(Achievement achievement, AchievementProgress achievementProgress) {
        if (achievementProgress.getCurrentPoints() >= achievement.getPoints()) {
            achievementService.giveAchievement(achievement, achievementProgress);
        }
    }


}
