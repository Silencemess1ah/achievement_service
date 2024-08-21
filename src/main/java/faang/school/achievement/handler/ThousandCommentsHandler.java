package faang.school.achievement.handler;
import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.events.CommentEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Slf4j
//@RequiredArgsConstructor
public class ThousandCommentsHandler extends CommentEventHandler{
//    private final ExecutorService cachedExecutorService;
    private final ReentrantLock lock = new ReentrantLock(true);

    @Value("${spring.data.redis.cache.title.expert}")
    private String achievementTitle;

    public ThousandCommentsHandler(AchievementCache cache, AchievementService service, ExecutorService cachedExecutorService) {
        super(cache, service, cachedExecutorService);
    }

    @Override
    public Achievement getAchievement() {
        return cache.get(achievementTitle);
    }

    @Override
    @Async
    public void handle(CommentEvent event) {
        handleAchievement(event);
//        cachedExecutorService.submit(() ->{handleAchievement(event);});
    }

    private void handleAchievement(CommentEvent event){
        long userId = event.getAuthorId();
        Achievement achievement = getAchievement();
        if (!hasUserGotAchievement(userId,achievement)){
            log.info("creating progress if necessary for user {}, achievement {}",userId, achievement.getDescription());
            service.createProgressIfNecessary(userId,achievement.getId());
            log.info("started to get progress for achievement {} of user {}",achievement.getDescription(), userId);
            AchievementProgress progress = service.getProgress(userId,achievement.getId());
            log.info("got progress for achievement {} successfully", achievement.getDescription());
            progress.increment();
            long points = progress.getCurrentPoints();
            if (points >= achievement.getPoints()) {
                service.giveAchievement(userId,achievement.getId());
            }
        }
    }
}
