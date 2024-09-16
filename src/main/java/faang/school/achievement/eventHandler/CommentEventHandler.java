package faang.school.achievement.eventhandler;

import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.CommentEvent;
import faang.school.achievement.service.achievement.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public abstract class CommentEventHandler implements EventHandler<CommentEvent> {

    private final AchievementCache achievementCache;
    private final AchievementService achievementService;
    private final String achievementName;

    @Override
    @Transactional
    public void handle(CommentEvent event) {
        Achievement achievement = achievementCache.get(achievementName);
        long achievementId = achievement.getId();
        long userId = event.getAuthorId();
        if (!achievementService.hasAchievement(userId, achievementId)) {
            achievementService.createProgressIfNecessary(userId, achievementId);
            AchievementProgress progress = achievementService.getProgress(userId, achievementId);
            progress.increment();
            achievementService.updateProgress(progress);
            if (progress.getCurrentPoints() >= achievement.getPoints()) {
                achievementService.giveAchievement(userId, achievement);
            }
        }
    }


}
