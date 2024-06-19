package faang.school.achievement.service;

import faang.school.achievement.exception.DataNotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;

    public boolean hasAchievement(long userId, long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    public Achievement getAchievement(long achievementId) {
        return achievementRepository
                .findById(achievementId)
                .orElseThrow(() -> {
                    String message = String.format("Achievement with id: %s not found", achievementId);
                    return new DataNotFoundException(message);
                });
    }

    @Transactional
    public void createProgressIfNecessary(long userId, Long achievementId) {
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
    }


    @Transactional
    public List<AchievementProgress> incrementProgressPoints(long userId) {
        List<AchievementProgress> userAchievementsProgress = achievementProgressRepository
                .findByUserId(userId);

        return userAchievementsProgress.stream()
                .peek(AchievementProgress::increment)
                .peek(achievementProgressRepository::save)
                .toList();
    }

    @Transactional
    public void giveAchievement(Achievement achievement, long userId) {
        UserAchievement userAchievement = UserAchievement.builder()
                .achievement(achievement)
                .userId(userId)
                .build();

        userAchievementRepository.save(userAchievement);
    }
}
