package faang.school.achievement.service.achievement;

import faang.school.achievement.dto.achievement.AchievementDto;
import faang.school.achievement.dto.achievement.AchievementFilterDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AchievementService {

    List<AchievementDto> getAchievements(AchievementFilterDto filters);

    AchievementDto getAchievementByAchievementId(long achievementId);

    @Transactional(readOnly = true)
    AchievementDto getAchievementByTitle(String title);

    boolean hasAchievement(long userId, long achievementId);

    void createProgressIfNecessary(long userId, long achievementId);

    AchievementProgress getProgress(long userId, long achievementId);

    void giveAchievement(long userId, Achievement achievement);
}
