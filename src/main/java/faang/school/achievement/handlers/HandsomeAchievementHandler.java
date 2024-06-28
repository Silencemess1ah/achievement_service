package faang.school.achievement.handlers;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.ProfilePicEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class HandsomeAchievementHandler implements EventHandler<ProfilePicEvent> {

    private static final String ACHIEVE_NAME = "HANDSOME";
    private final AchievementService achievementService;
    private final AchievementCache achievementCache;

    @Async
    @Transactional
    @Override
    public void handleEvent(ProfilePicEvent event) {
        Achievement achievement = achievementCache.get(ACHIEVE_NAME);
        long achieveId = achievement.getId();
        long userId = event.getUserId();

        if (!achievementService.hasAchievement(userId, achieveId)) {
            achievementService.createProgressIfNecessary(userId, achieveId);

            AchievementProgress progress = achievementService.getProgress(userId, achieveId);
            progress.increment();
            achievementService.updateProgress(progress);

            if (achievement.getPoints() == progress.getCurrentPoints()) {
                achievementService.giveAchievement(userId, achievement);
            }
        }
    }
}
