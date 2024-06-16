package faang.school.achievement.service.achievement;

import faang.school.achievement.dto.achievement.AchievementDto;
import faang.school.achievement.dto.achievement.AchievementFilterDto;

import java.util.List;

public interface AchievementService {

    List<AchievementDto> getAchievements(AchievementFilterDto filters);

    AchievementDto getAchievementByAchievementId(long achievementId);
}
