package faang.school.achievement.handler.post;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.post.PostEvent;
import faang.school.achievement.handler.EventHandler;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

@Slf4j
@RequiredArgsConstructor
public abstract class PostEventHandler implements EventHandler<PostEvent> {
    private final AchievementCache achievementCache;
    private final AchievementService achievementService;
    private final String title;
    private final AchievementProgressRepository achievementProgressRepository;

    @Async("achievementHandlerTaskExecutor")
    protected void processEvent(PostEvent postEvent) {
        long userId = postEvent.getAuthorId();
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