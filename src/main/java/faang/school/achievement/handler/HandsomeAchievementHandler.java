package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.ProfilePicEvent;
import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HandsomeAchievementHandler implements EventHandler<ProfilePicEvent> {
    private final AchievementService achievementService;
    private final AchievementCache achievementCache;

    @Value("${spring.achievement-handler.handsome-achievement-handler.achievement-name}")
    private String achievementTitle;

    @Async
    public void handle(ProfilePicEvent event) {
        Achievement achievement = achievementCache.getAchievementByTitle(achievementTitle)
                .orElseThrow(() -> {
                    String errorMessage = String.format("Achievement with title %s not found", achievementTitle);
                    log.error(errorMessage);
                    return new EntityNotFoundException(errorMessage);
                });
        long achievementId = achievement.getId();
        long userId = event.getUserId();

        if (!achievementService.hasAchievement(userId, achievementId)) {
            achievementService.createProgressIfNecessary(userId, achievementId);
        }

        AchievementProgress achievementProgress = achievementService.getProgress(userId, achievementId);
        achievementProgress.increment();

        if (achievementProgress.getAchievement().getPoints() <= achievement.getPoints()) {
            achievementService.giveAchievement(userId, achievementId);
        }
    }
}
