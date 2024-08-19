package faang.school.achievement.service;

import faang.school.achievement.exception.DataNotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;

    public boolean hasAchievement(long userId, long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Transactional
    public void createProgressIfNecessary(long userId, long achievementId) {
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
    }

    public AchievementProgress getProgress(long userId, long achievementId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseThrow(() -> new DataNotFoundException("Achievement progress not found"));
    }

    @Transactional
    public void giveAchievement(Achievement achievement, long userId) {
        UserAchievement userAchievement = UserAchievement.builder()
                .achievement(achievement)
                .userId(userId)
                .build();

        userAchievementRepository.save(userAchievement);
    }

    public AchievementProgress incrementAchievementProgress(long userId, long achievementId) {
        AchievementProgress achievementProgress = getProgress(userId, achievementId);
        achievementProgress.increment();
        return achievementProgressRepository.save(achievementProgress);
    }
}
