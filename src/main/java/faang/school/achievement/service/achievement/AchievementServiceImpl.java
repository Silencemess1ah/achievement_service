package faang.school.achievement.service.achievement;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.exception.NotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;
    private final AchievementCache achievementCache;

    @Override
    @Transactional
    public void doWork(long userId, long achievementId) {
        if (!hasAchievement(userId, achievementId)) {
            Achievement achievement = getAchievementById(achievementId);
            AchievementProgress progress = createProgressIfNecessary(userId, achievement);
            incrementAchievementProgress(progress);
            updateAchievementProgress(progress);
            giveAchievementIfEnoughScore(userId, progress, achievement);
        }
    }

    @Override
    public boolean hasAchievement(long userId, long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Override
    @Transactional
    public AchievementProgress getProgress(long userId, Achievement achievement) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievement.getId())
                .orElseGet(() -> saveProgressWithUserIdAndAchievement(userId, achievement));
    }

    @Override
    @Transactional
    public void giveAchievement(UserAchievement userAchievement) {
        if (!userAchievementRepository.existsByUserIdAndAchievementId(userAchievement.getUserId(),
                userAchievement.getAchievement().getId())) {
            userAchievementRepository.save(userAchievement);
        }
    }

    @Override
    public Achievement getAchievementByName(String name) {
        return achievementCache.getAchievement(name)
                .orElseThrow(() -> new NotFoundException("No achievement with name " + name));
    }

    @Override
    public Achievement getAchievementById(long id) {
        return achievementCache.getAchievementById(id)
                .orElseThrow(() -> new NotFoundException("No achievement with ID " + id));
    }

    @Override
    public void incrementAchievementProgress(AchievementProgress achievementProgress) {
        AtomicLong currentPoints = new AtomicLong(achievementProgress.getCurrentPoints());
        currentPoints.incrementAndGet();
        achievementProgress.setCurrentPoints(currentPoints.get());
    }

    @Override
    public void updateAchievementProgress(AchievementProgress achievementProgress) {
        achievementProgressRepository.save(achievementProgress);
    }

    @Override
    public AchievementProgress saveProgressWithUserIdAndAchievement(long userId, Achievement achievement) {
        return achievementProgressRepository.save(AchievementProgress.builder()
                .userId(userId)
                .achievement(achievement)
                .currentPoints(0L)
                .build()
        );
    }

    @Override
    @Transactional
    public AchievementProgress createProgressIfNecessary(long userId, Achievement achievement) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievement.getId())
                .orElseGet(() -> saveProgressWithUserIdAndAchievement(userId, achievement));
    }

    @Transactional
    public void giveAchievementIfEnoughScore(long userId, AchievementProgress progress, Achievement achievement) {
        if (progress.getCurrentPoints() >= achievement.getPoints()) {
            UserAchievement userAchievement = UserAchievement.builder()
                    .userId(userId)
                    .achievement(achievement)
                    .build();
            giveAchievement(userAchievement);
        }
    }
}
