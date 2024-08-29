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


    @Override
    public void handleEvent(PostEvent event) {

        Achievement achievementFromCache = achievementCacheService.getAchievementByTitle(achievementTitle);
        Long userId = event.getAuthorId();
        long achievementId = achievementFromCache.getId();

        if (achievementService.hasAchievement(userId, achievementId)) {
            log.info("User {} already has achievement {}", userId, achievementTitle);
            return;
        }

        achievementService.createProgressIfNecessary(event.getAuthorId(), achievementId);

        AchievementProgress achievementProgress = achievementService.incrementAchievementProgress(event.getAuthorId(), achievementId);

        tryGiveAchievement(achievementProgress, achievementFromCache);
    }

    private void tryGiveAchievement(AchievementProgress achievementProgress, Achievement achievementFromCache) {
        if (achievementProgress.getCurrentPoints() >= achievementFromCache.getPoints()) {
            achievementService.giveAchievement(achievementFromCache, achievementProgress.getUserId());
        }
    }
}
