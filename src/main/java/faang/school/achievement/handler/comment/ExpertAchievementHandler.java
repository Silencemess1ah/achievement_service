package faang.school.achievement.handler.comment;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.CommentEvent;
import faang.school.achievement.handler.EventHandler;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ExpertAchievementHandler implements EventHandler {
    private final AchievementService achievementService;
    private final AchievementCache achievementCache;

    @Override
    @Async
    public void checkAndGetAchievement(CommentEvent commentEvent) {
        String titleAchievement = "EXPERT";
        Achievement achievement = achievementCache.getAchievementByTitle(titleAchievement).orElseThrow();
        long userId = commentEvent.getAuthorId();
        long achievementId = achievement.getId();

        if (!achievementService.hasAchievement(userId, achievementId)) {
            achievementService.createProgressIfNecessary(userId, achievementId);
            AchievementProgress achievementProgress = achievementService.getProgress(userId, achievementId).orElseThrow();
            achievementProgress.increment();
            if (achievementProgress.getCurrentPoints() == achievement.getPoints()) {
                achievementService.giveAchievement(achievement, userId);
            }
        }
    }
}