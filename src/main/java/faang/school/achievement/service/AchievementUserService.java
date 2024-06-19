package faang.school.achievement.service;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AchievementUserService {
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementCache achievementCache;

    public boolean hasAchievement(Long userId, Long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    public void giveAchievement(Long userId, Achievement achievementId) {
        achievementCache.get(achievementId.toString())
                .ifPresentOrElse(achievement -> {
                    UserAchievement userAchievement = new UserAchievement();
                    userAchievement.setUserId(userId);
                    userAchievement.setAchievement(achievement);
                    userAchievementRepository.save(userAchievement);
                }, () -> {
                    throw new NoSuchElementException("Achievement not found");
                });
    }

    public Achievement getAchievement(Long achievementId) {
        Optional<Achievement> optionalAchievement = achievementCache.get(achievementId.toString());
        return optionalAchievement.orElseThrow(() -> new NoSuchElementException("Achievement not found"));
    }
}
