package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;
    private final AchievementProgressMapper achievementProgressMapper;

    public boolean hasAchievement(long userId, long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    public void createProgressIfNecessary(long userId, long achievementId) {
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
    }

    public AchievementProgressDto getProgress(long userId, long achievementId) {
        return achievementProgressMapper.toDto(
                achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId).orElseThrow());
    }

    public void giveAchievement(long userId, Achievement achievement) {
        UserAchievement userAchievement = new UserAchievement();

        userAchievement.setUserId(userId);
        userAchievement.setAchievement(achievement);

        userAchievementRepository.save(userAchievement);
    }
}
