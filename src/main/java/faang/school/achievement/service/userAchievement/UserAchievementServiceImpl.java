package faang.school.achievement.service.userAchievement;

import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserAchievementServiceImpl implements UserAchievementService {
    private final UserAchievementRepository userAchievementRepository;

    @Override
    public boolean hasAchievement(long userId, long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Override
    @Transactional
    public void assignAchievement(UserAchievement userAchievement) {
        if (hasAchievement(userAchievement.getUserId(), userAchievement.getAchievement().getId())) {
            userAchievementRepository.save(userAchievement);
        }
    }
}