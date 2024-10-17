package faang.school.achievement.service;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;

    @Transactional(readOnly = true)
    public Achievement findAchievement(String title) {
        return achievementRepository.findByTitle(title).orElseThrow();
    }

    @Transactional
    public void saveAchievementProgress(AchievementProgress progress) {
        achievementProgressRepository.save(progress);
    }

    @Transactional
    public void saveUserAchievement(UserAchievement userAchievement) {
        userAchievementRepository.save(userAchievement);
    }
}
