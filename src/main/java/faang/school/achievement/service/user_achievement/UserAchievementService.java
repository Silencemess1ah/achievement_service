package faang.school.achievement.service.user_achievement;

import faang.school.achievement.dto.achievement.AchievementDto;
import faang.school.achievement.dto.achievement.UserAchievementDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserAchievementService {
    List<UserAchievementDto> getAchievementsByUserId(long userId);

    @Transactional
    void giveAchievement(long userId, AchievementDto achievement);

    @Transactional(readOnly = true)
    boolean hasAchievement(long userId, long achievementId);
}
