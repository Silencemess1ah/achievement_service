package faang.school.achievement.handler.comment;

import faang.school.achievement.dto.comment.CommentEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.service.achievement.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EvilCommenterAchievementHandler extends CommentEventHandler {
    private static final String EVIL_COMMENTER = "EVIL COMMENTER";

    private final AchievementService achievementService;
//    private final AchievementCache achievementCache;

    @Async
    @Override
    public void verifyAchievement(CommentEventDto commentEventDto) {
//        Achievement evilCommenter = achievementCache.get(EVIL_COMMENTER);
        Achievement evilCommenter = new Achievement();
        long userId = commentEventDto.getCommentAuthorId();
        long achievementId = evilCommenter.getId();
        if (!achievementService.hasAchievement(userId, achievementId)) {
            achievementService.createProgressIfNecessary(userId, achievementId);
            AchievementProgress progress = achievementService.getProgress(userId, achievementId);
            long progressPoints = progress.getCurrentPoints();
            progress.setCurrentPoints(progressPoints + 1);
            achievementService.saveAchievementProgress(progress);
            if (progress.getCurrentPoints() == evilCommenter.getPoints()) {
                UserAchievement userAchievement = UserAchievement.builder()
                        .achievement(evilCommenter)
                        .userId(userId)
                        .build();
                log.debug("Giving achievement {}! To user {}!", EVIL_COMMENTER, userId);
                achievementService.giveAchievement(userAchievement);
            }
        }
    }
}
