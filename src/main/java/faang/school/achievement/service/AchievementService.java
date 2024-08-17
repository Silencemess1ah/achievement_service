package faang.school.achievement.service;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementProgressRepository achievementProgressRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementCache achievementCache;

    @Transactional
    public void incrementAchievementProgress(long userId, long achievementId) {
        Optional<AchievementProgress> progress =
                achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId);
        if (progress.isPresent()) {
            progress.get().increment();
        } else {
            achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
            achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId).ifPresent(
                    AchievementProgress::increment);
        }
    }

    @Transactional
    public void createProgressIfNecessary(long userId, long achievementId) {
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
    }

    @Transactional(readOnly = true)
    public long getProgress(long userId, long achievementId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId).orElseThrow(() -> {
            log.info("Could not get progress from achievement with id {}", achievementId);
            return new EntityNotFoundException("Achievement with id " + achievementId + " not found");
        }).getCurrentPoints();
    }

    @Transactional(readOnly = true)
    public boolean hasAchievement(long userId, long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Transactional
    public void giveAchievement(long userId, Achievement achievement) {
        UserAchievement userAchievement = UserAchievement.builder()
                .userId(userId)
                .achievement(achievement)
                .build();
        userAchievementRepository.save(userAchievement);
    }

    public Achievement getAchievementFromCache(String achievementTitle) {
        return achievementCache.getAchievement(achievementTitle);
    }
}