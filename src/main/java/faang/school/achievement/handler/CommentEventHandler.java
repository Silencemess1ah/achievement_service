package faang.school.achievement.handler;

import faang.school.achievement.dto.CommentEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public abstract class CommentEventHandler {

    protected final AchievementService achievementService;

    public abstract void handle(CommentEventDto event);

    protected void handleProgress(CommentEventDto event, Achievement achievement) {
        long userId = event.getCommentAuthorId();
        if (!achievementService.hasAchievement(userId, achievement.getId())) {
            achievementService.createProgressIfNecessary(userId, achievement.getId());
            achievementService.incrementAchievementProgress(userId, achievement.getId());
            long progress = achievementService.getProgress(userId, achievement.getId());
            if (achievement.getPoints() == progress) {
                achievementService.giveAchievement(userId, achievement);
            }
        }
    }
}