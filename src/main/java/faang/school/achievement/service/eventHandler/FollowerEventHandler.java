package faang.school.achievement.service.eventHandler;

import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.FollowerEvent;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public abstract class FollowerEventHandler implements EventHandler<FollowerEvent> {
    private AchievementCache cache;
    private AchievementService achievementService;
    private AchievementMapper mapper;

    public void process(FollowerEvent event, String achievementTitle) {
        Achievement achievement = cache.get(achievementTitle);
        long userId = event.getFolloweeId();
        long achievementId = achievement.getId();

        if (achievementService.hasAchievement(userId, achievementId)) {
            return;
        }
        achievementService.createProgressIfNecessary(userId, achievementId);
        AchievementProgressDto progressDto = achievementService.getProgress(userId, achievementId);
        AchievementProgress progress = mapper.toAchievementProgress(progressDto);
        progress.increment();
        achievementService.saveProgress(progress);
        if (progress.getCurrentPoints() >= achievement.getPoints()) {
            achievementService.giveAchievement(userId, achievement);
        }
    }
}
