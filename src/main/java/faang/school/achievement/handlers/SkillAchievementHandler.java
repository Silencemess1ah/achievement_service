package faang.school.achievement.handlers;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.SkillAcquiredEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
public abstract class SkillAchievementHandler implements EventHandler {

    private String achieveName;

    private final AchievementCache achievementCache;
    private final AchievementService achievementService;

    @Async
    @Transactional
    @Override
    public void handleAchievement(SkillAcquiredEvent event) {
        Achievement achievement = achievementCache.get(achieveName);
        long achievementId = achievement.getId();
        long userId = event.getActorId();

        if (!achievementService.hasAchievement(userId, achievement.getId())) {
            achievementService.createProgressIfNecessary(userId, achievementId);

            AchievementProgress progress = achievementService.getProgress(userId,achievementId);
            progress.increment();
            achievementService.updateProgress(progress);

            if (progress.getCurrentPoints() == achievement.getPoints()) {
                achievementService.giveAchievement(userId, achievement);
            }
        }
    }
}
