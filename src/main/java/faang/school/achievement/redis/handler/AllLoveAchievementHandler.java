package faang.school.achievement.redis.handler;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class AllLoveAchievementHandler extends LikeEventHandler {
    private Achievement achievement;
    @Value("${achievement.all-love.id}")
    private Long achievementId;


    @PostConstruct
    public void setUp() {
        achievement = achievementService.getAchievement(achievementId);
    }

    public AllLoveAchievementHandler(AchievementService achievementService) {
        super(achievementService);
    }

    @Override
    protected void tryGiveAchievement(List<AchievementProgress> achievementProgresses) {
        AchievementProgress achievementProgress = achievementProgresses.stream()
                .filter(currentAchievementProgress -> currentAchievementProgress.getAchievement().equals(achievement))
                .findFirst()
                .orElseThrow(getAchievementProgressNotFoundException(achievementId));

        long userId = achievementProgress.getUserId();

        if (achievementService.hasAchievement(userId, achievementId)) {
            return;
        }

        if (achievementProgress.getCurrentPoints() >= achievement.getPoints()) {
            achievementService.giveAchievement(achievement, userId);
            log.info("User with id: {} received achievement {}", userId, achievement.getTitle());
        }
    }

    @Override
    public void createProgressIfNecessary(long userId) {
        achievementService.createProgressIfNecessary(userId, achievementId);
    }
}
