package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.config.context.UserContext;
import faang.school.achievement.events.CommentEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Component
@Slf4j
public class ThousandCommentsHandler extends CommentEventHandler {

    @Value("${spring.data.redis.cache.title.expert}")
    private String achievementTitle;

    public ThousandCommentsHandler(AchievementCache cache, AchievementService service, ExecutorService cachedExecutorService, UserContext userContext) {
        super(cache, service, cachedExecutorService, userContext);
    }

    @Override
    public Achievement getAchievement() {
        return cache.get(achievementTitle);
    }

    @Override
    @Async("cachedExecutorService")
    public void handle(CommentEvent event) {
        userContext.setUserId(event.getAuthorId());
        handleAchievement(event);
    }

    private void handleAchievement(CommentEvent event) {
        long userId = event.getAuthorId();
        Achievement achievement = getAchievement();
        if (!hasUserGotAchievement(userId, achievement)) {
            log.info("creating progress if necessary for user {}, achievement {}", userId, achievement.getDescription());
            service.createProgressIfNecessary(userId, achievement.getId());
            log.info("started to get progress for achievement {} of user {}", achievement.getDescription(), userId);
            AchievementProgress progress = service.getProgress(userId, achievement.getId());
            log.info("got progress for achievement {} successfully", achievement.getDescription());
            service.incrementCurrentPointsForUser(progress.getId());
            long points = progress.getCurrentPoints();
            if (points >= achievement.getPoints() - 1) {
                service.giveAchievement(userId, achievement.getId());
                log.info("You got achievement {} successfully", achievement.getDescription());
            } else {
                log.info("{} comments remains for getting achievement {}", achievement.getPoints() - points - 1, achievement.getDescription());
            }
        }
    }
}
