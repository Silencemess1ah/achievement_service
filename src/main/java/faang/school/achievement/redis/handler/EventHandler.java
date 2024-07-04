package faang.school.achievement.redis.handler;

import faang.school.achievement.dto.EventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

@Slf4j
@RequiredArgsConstructor
public abstract class EventHandler {
    protected final AchievementService achievementService;
    protected Achievement achievement;
    protected String type;

    @Async(value = "taskExecutor")
    public void handleEvent(EventDto event) {
        log.info("Received an {} event from {}_topic for user with id: {}", type, type, event.getUserId());

        createProgressIfNecessary(event.getUserId());

        AchievementProgress achievementProgress = achievementService
                .incrementProgressPoints(event.getUserId(), achievement.getId());

        log.info("All {} achievements progresses have been incremented", type);

        tryGiveAchievement(achievementProgress);
    }


    public void tryGiveAchievement(AchievementProgress achievementProgress) {
        long userId = achievementProgress.getUserId();

        if (achievementService.hasAchievement(userId, achievement.getId()) ||
                achievementProgress.getCurrentPoints() < achievement.getPoints()) {
            return;
        }

        achievementService.giveAchievement(achievement, userId);
        log.info("User with id: {} received achievement {}", userId, achievement.getTitle());
    }


    public void createProgressIfNecessary(long userId) {
        achievementService.createProgressIfNecessary(userId, achievement.getId());
    }
}
