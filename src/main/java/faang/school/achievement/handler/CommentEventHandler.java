package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.config.context.UserContext;
import faang.school.achievement.events.CommentEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.service.AchievementService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
@Component
@RequiredArgsConstructor
@Getter
@Slf4j
public abstract class CommentEventHandler implements EventHandler<CommentEvent> {
    protected final AchievementCache cache;
    protected final AchievementService service;
    protected final ExecutorService cachedExecutorService;
    protected final UserContext userContext;

    public abstract Achievement getAchievement();

    @Override
    public abstract void handle(CommentEvent event);

    public boolean hasUserGotAchievement(long userId, Achievement achievement) {
        boolean gotAchievement = service.hasAchievement(userId, achievement.getId());
        if (gotAchievement) {
            log.info("already have achievement {}", achievement.getDescription());
        } else {
            log.info("does not have achievement {}", achievement.getDescription());
        }
        return gotAchievement;
    }
}
