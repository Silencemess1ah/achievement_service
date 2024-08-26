package faang.school.achievement.service.eventhandler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.event.Event;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractEventHandler<T extends Event> implements EventHandler<T> {

    private final String achievementTitle;

    private final AchievementCache achievementCache;

    private final AchievementService achievementService;

    @Async("achievementsPool")
    @Override
    public void handle(T event) {
        Achievement achievement = achievementCache.get(achievementTitle);

        long userId = event.getUserId();
        long achievementId = achievement.getId();

        if (achievementService.userHasAchievement(userId, achievementId)) {
            return;
        }
        achievementService.createProgressIfNecessary(userId, achievementId);
        AchievementProgress progress
                = achievementService.getProgress(userId, achievementId);
        progress.increment();
        achievementService.saveProgress(progress);
        if (progress.getCurrentPoints() >= achievement.getPoints()) {
            achievementService.giveAchievement(userId, achievement);
        }
    }
}
