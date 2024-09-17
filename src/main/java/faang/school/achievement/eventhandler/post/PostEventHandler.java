package faang.school.achievement.eventhandler.post;

import faang.school.achievement.event.post.PostEvent;
import faang.school.achievement.eventhandler.EventHandler;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.achievement.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public abstract class PostEventHandler implements EventHandler<PostEvent> {
    private final AchievementService achievementService;
    private final AchievementCache cache;
    private final String achievementName;

    @Override
    @Transactional
    public void handle(PostEvent event) {
        Achievement achievement = cache.get(achievementName);
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
