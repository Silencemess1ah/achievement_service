package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AchievementService {
    private final AchievementProgressService achievementProgressService;
    private final AchievementUserService achievementUserService;

    public boolean hasAchievement(Long userId, Long achievementId) {
        return achievementUserService.hasAchievement(userId, achievementId);
    }

    public void createProgressIfNecessary(Long userId, Long achievementId) {
        achievementProgressService.createProgressIfNecessary(userId, achievementId);
    }

    public AchievementProgress getProgress(Long userId, Long achievementId) {
        return achievementProgressService.getProgress(userId, achievementId);
    }

    public void saveProgress(AchievementProgress progress) {
        achievementProgressService.saveProgress(progress);
    }

    public Achievement getAchievement(Long achievementId) {
        return achievementUserService.getAchievement(achievementId);
    }

    public void giveAchievement(Long userId, Achievement achievementId) {
        achievementUserService.giveAchievement(userId, achievementId);
    }

    public void incrementProgress(Long userId, Long achievementId) {
        achievementProgressService.incrementProgress(userId, achievementId);
    }
}
