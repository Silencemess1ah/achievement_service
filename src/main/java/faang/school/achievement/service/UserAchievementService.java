package faang.school.achievement.service;

import faang.school.achievement.annotation.PublishAchievementEvent;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.AchievementTitle;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAchievementService {
    private final UserAchievementRepository userAchievementRepository;


    @PublishAchievementEvent
    @Transactional
    public UserAchievement createAndSaveNewUserAchievement(AchievementProgress progress) {
        UserAchievement userAchievement = UserAchievement.builder()
                .achievement(progress.getAchievement())
                .userId(progress.getUserId())
                .build();
        userAchievementRepository.save(userAchievement);
        return userAchievement;
    }

    @Transactional(readOnly = true)
    public boolean hasAchievement(Long userId, AchievementTitle title) {
        return userAchievementRepository.hasAchievement(userId, title);
    }
}
