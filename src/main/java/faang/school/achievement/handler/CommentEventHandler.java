package faang.school.achievement.handler;

import faang.school.achievement.event.CommentEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.EventType;
import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import faang.school.achievement.service.UserEventCounterService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public abstract class CommentEventHandler extends AbstractEventHandler<CommentEvent> {


    public CommentEventHandler(AchievementService achievementService,
                               AchievementProgressService achievementProgressService,
                               UserAchievementService userAchievementService,
                               UserEventCounterService userEventCounterService) {
        super(achievementService, achievementProgressService, userAchievementService, userEventCounterService);
    }

    @Async("asyncExecutor")
    public void checkAchievement(CommentEvent event) {
        Achievement achievement = achievementService.getAchievementFromCache(getAchievementName());
        if (userAchievementService.hasAchievement(event.getCommentAuthorId(), achievement.getId())) {
            achievementProgressService.createProgressIfNecessary(event.getCommentAuthorId(), achievement.getId());
            AchievementProgress progress = achievementProgressService.getProgress(event.getCommentAuthorId(), achievement.getId());
            Long num = userEventCounterService.getProgress(event.getCommentAuthorId(), EventType.COMMENT_EVENT);
            if (num > achievement.getPoints()) {
                progress.setCurrentPoints(achievement.getPoints());
            } else {
                progress.setCurrentPoints(num);
            }
            achievementProgressService.saveAchievementProgress(progress);
            if (progress.getCurrentPoints() >= achievement.getPoints()) {
                userAchievementService.giveAchievement(event.getCommentAuthorId(), achievement);
            }
        }
    }

    @Override
    public abstract String getAchievementName();
}