package faang.school.achievement.service;

import faang.school.achievement.dto.achievement.AchievementProgressDto;
import faang.school.achievement.mapper.achievement.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementProgressRepository achievementProgressRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;

    public AchievementProgressDto getAchievementProgress(long userId, long achievementId) {
        return achievementMapper.toAchievementProgressDto(getProgress(userId,achievementId));
    }

    public boolean hasAchievement(long userId, long achievementId){
        return userAchievementRepository.existsByUserIdAndAchievementId(userId,achievementId);
    }

    public void createProgressIfNecessary(long userId, long achievementId){
        achievementProgressRepository.createProgressIfNecessary(userId,achievementId);
    }

    public AchievementProgress getProgress(long userId, long achievementId){
        return achievementProgressRepository
                .findByUserIdAndAchievementId(userId, achievementId)
                .orElseThrow(() -> new EntityNotFoundException("There achievement with id " +
                        achievementId + " found!"));
    }

    public void giveAchievement(Achievement achievement){
        achievementRepository.save(achievement);
    }
}
