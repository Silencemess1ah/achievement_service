package faang.school.achievement.handler;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AchievementProgressHandler {
    private final AchievementService achievementService;

    public void handleAchievementProgress(Long userId, Achievement achievement) {
        Long achievementId = achievement.getId();
        achievementService.createProgressIfNecessary(userId, achievementId);
        AchievementProgress progress = achievementService.getProgress(userId, achievementId);
        progress.increment();
        if (progress.getCurrentPoints() >= achievement.getPoints()) {
            achievementService.giveAchievement(userId, achievementId);
            log.info("User %d had got an achievement %d".formatted(userId, achievementId));
        }
        achievementService.updateProgress(progress);
    }
}
