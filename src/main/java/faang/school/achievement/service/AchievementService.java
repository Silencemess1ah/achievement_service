package faang.school.achievement.service;

import faang.school.achievement.config.context.UserContext;
import faang.school.achievement.exception.DataNotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final UserContext userContext;

    @Transactional
    public void saveAchievementToUser(long achievementId) {

        long userId = userContext.getUserId();

        Achievement achievement =
                achievementRepository.findById(achievementId).orElseThrow(() -> {
                    log.error("Achievement not found by id: {}", achievementId);
                    return new DataNotFoundException("Achievement not found");
                });

        userAchievementRepository.save(
                UserAchievement.builder()
                        .achievement(achievement)
                        .userId(userId)
                        .build());
    }
}



