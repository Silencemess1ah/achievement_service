package faang.school.achievement.repository;

import faang.school.achievement.jpa.AchievementProgressJpaRepository;
import faang.school.achievement.model.AchievementProgress;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AchievementProgressRepository {

    private final AchievementProgressJpaRepository achievementProgressJpaRepository;

    public AchievementProgress findByUserIdAndAchievementId(long userId, long achievementId) {
        return achievementProgressJpaRepository.findByUserIdAndAchievementId(userId, achievementId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Achievement progress with ID: %d for user with ID: %d not found", achievementId, userId)));
    }
}
