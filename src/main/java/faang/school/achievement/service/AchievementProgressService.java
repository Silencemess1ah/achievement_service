package faang.school.achievement.service;

import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class AchievementProgressService {
    private final AchievementProgressRepository achievementProgressRepository;

    public void createProgressIfNecessary(Long userId, Long achievementId) {
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
    }

    public AchievementProgress getProgress(Long userId, Long achievementId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Achievement progress not found"));
    }

    public void saveProgress(AchievementProgress progress) {
        achievementProgressRepository.save(progress);
    }

    public void incrementProgress(Long userId, Long achievementId){
        AchievementProgress progress = getProgress(userId, achievementId);
        AtomicLong currentPoints = new AtomicLong(progress.getCurrentPoints());
        currentPoints.incrementAndGet();
        progress.setCurrentPoints(currentPoints.get());
        saveProgress(progress);
    }
}
