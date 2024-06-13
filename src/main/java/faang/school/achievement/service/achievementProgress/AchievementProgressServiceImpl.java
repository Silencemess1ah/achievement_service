package faang.school.achievement.service.achievementProgress;

import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicLong;

@Service
@AllArgsConstructor
public class AchievementProgressServiceImpl implements AchievementProgressService {
    private final AchievementProgressRepository achievementProgressRepository;

    @Override
    @Transactional
    public void createProgressIfNecessary(long userId, long achievementId) {
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
    }

    @Override
    public AchievementProgress findByUserIdAndAchievementId(long userId, long achievementId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("achievement progress with user id = %d and achievement id = %d not found", userId, achievementId)));
    }

    @Override
    @Transactional
    public void updateAchievementProgressPoints(AchievementProgress achievementProgress) {
        incrementAchievementProgressPoints(achievementProgress);
        achievementProgressRepository.save(achievementProgress);
    }

    private void incrementAchievementProgressPoints(AchievementProgress achievementProgress) {
        AtomicLong currentPoints = new AtomicLong(achievementProgress.getCurrentPoints());
        achievementProgress.setCurrentPoints(currentPoints.incrementAndGet());
    }
}