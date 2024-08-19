package faang.school.achievement.redis.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.event.PostEvent;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public abstract class PostEventHandler implements EventHandler<PostEvent> {

    protected final AchievementService achievementService;
    protected final AchievementCache achievementCacheService;
    protected final String achievementTitle;

    protected abstract void createProgressIfNecessary(PostEvent event, long achievementId);

    protected abstract void tryGiveAchievement(AchievementProgress achievementProgress, Achievement achievement);

    protected abstract AchievementProgress incrementAchievementProgress(PostEvent event, long achievementId);

    @Override
    public void handleEvent(PostEvent event) {

        Achievement achievementFromCache = getAchievementFromCache();
        Long userId = event.getAuthorId();
        long achievementId = achievementFromCache.getId();

        if (achievementService.hasAchievement(userId, achievementId)) {
            log.info("User {} already has achievement {}", userId, achievementTitle);
            return;
        }

        createProgressIfNecessary(event, achievementId);

        AchievementProgress achievementProgress = incrementAchievementProgress(event, achievementId);

        tryGiveAchievement(achievementProgress, achievementFromCache);
    }

    private Achievement getAchievementFromCache() {
        return achievementCacheService.getAchievementByTitle(achievementTitle);
    }
}
