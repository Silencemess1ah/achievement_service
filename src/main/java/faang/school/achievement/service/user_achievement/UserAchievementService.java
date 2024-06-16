package faang.school.achievement.service.user_achievement;

import faang.school.achievement.dto.achievement.UserAchievementDto;

import java.util.List;

public interface UserAchievementService {
    List<UserAchievementDto> getAchievementsByUserId(long userId);
}
