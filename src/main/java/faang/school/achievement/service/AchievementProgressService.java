package faang.school.achievement.service;

import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AchievementProgressService {
    private final AchievementProgressRepository achievementProgressRepository;

    @Transactional(readOnly = true)
    public AchievementProgress getProgress(long userId, long achievementId){
        return achievementProgressRepository.findByUserIdAndAchievementId(userId,achievementId)
                .orElseThrow(() -> {
                    log.error("Achievement progress not found for user {} and achievement {}", userId, achievementId);
                    return new EntityNotFoundException("Achievement progress not found");
                });
    }

    @Transactional
    public void createProgressIfNecessary(long userId, long achievementId){
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
    }

    @Transactional
    public void saveAchievementProgress(AchievementProgress achievementProgress){
        achievementProgressRepository.save(achievementProgress);
    }
}
