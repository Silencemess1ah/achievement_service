package faang.school.achievement.messaging.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.Event;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractEventHandler implements EventHandler<Event> {
    private final AchievementCache achievementCache;
    private final AchievementService achievementService;
    private final String title;
    private final AchievementProgressRepository achievementProgressRepository;

    @Async("achievementHandlerTaskExecutor")
    public void processEvent(Event event) {
        long userId = event.getUserId();
        Achievement achievement = achievementCache.getAchievement(title);
        if (!achievementService.hasAchievement(userId, achievement.getId())) {
            achievementService.createProgressIfNecessary(userId, achievement.getId());
            AchievementProgress achievementProgress = achievementService.getProgress(userId, achievement.getId());
            achievementProgress.increment();

            achievementProgressRepository.save(achievementProgress);

            if (achievementProgress.getCurrentPoints() >= achievement.getPoints()) {
                achievementService.giveAchievement(userId, achievement);
            }
        }
    }
}