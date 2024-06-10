package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.event.MentorshipStartEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.service.achievementProgress.AchievementProgressService;
import faang.school.achievement.service.userAchievement.UserAchievementService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class SenseiAchievementHandler implements EventHandler<MentorshipStartEvent> {
    private final AchievementCache achievementCache;
    private final UserAchievementService userAchievementService;
    private final AchievementProgressService achievementProgressService;

    @Value("${event_handlers.sensei_achievement_handler.achievement_title}")
    private String achievementTitle;

    @Override
    public boolean canHandle(MentorshipStartEvent event) {
        return true;
    }

    @Override
    @Async
    @Retryable(value = {OptimisticLockingFailureException.class}, maxAttempts = 5, backoff = @Backoff(delay = 3000))
    public void handle(MentorshipStartEvent event) {
        Achievement achievement = achievementCache.get(achievementTitle)
                .orElseThrow(() -> new EntityNotFoundException(String.format("achievement with title %s not found", achievementTitle)));
        long userId = event.getMenteeId();
        long achievementId = achievement.getId();
        if (userAchievementService.hasAchievement(userId, achievementId)) {
            log.info("userAchievement with user id = {} and achievement id = {} already exist", userId, achievementId);
            return;
        }
        achievementProgressService.createProgressIfNecessary(userId, achievementId);
        AchievementProgress achievementProgress = achievementProgressService.findByUserIdAndAchievementId(userId, achievementId);
        achievementProgressService.updateAchievementProgressPoints(achievementProgress);
        Optional<UserAchievement> userAchievement = generateUserAchievement(achievementProgress, achievement);
        userAchievement.ifPresent(userAchievementService::assignAchievement);
    }
}