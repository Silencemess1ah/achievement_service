package faang.school.achievement.handler;

import faang.school.achievement.dto.CommentEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public abstract class CommentEventHandler implements EventHandler<CommentEventDto> {

    protected final AchievementService achievementService;

    @Async
    protected void handleProgress(CommentEventDto event, Achievement achievement) {
        long userId = event.getCommentAuthorId();
        achievementService.incrementAchievementProgress(userId, achievement.getId());
        long progress = achievementService.getProgress(userId, achievement.getId());
        if (achievement.getPoints() == progress) {
            achievementService.giveAchievement(userId, achievement);
        }
    }
}