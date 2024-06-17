package faang.school.achievement.service.achievement;

import faang.school.achievement.dto.achievement.AchievementDto;
import faang.school.achievement.dto.achievement.AchievementFilterDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AchievementService {

    List<AchievementDto> getAchievements(AchievementFilterDto filters);

    AchievementDto getAchievementByAchievementId(long achievementId);

    @Transactional(readOnly = true)
    AchievementDto getAchievementByTitle(String title);
}
