package faang.school.achievement.service.achievement_progress;

import faang.school.achievement.dto.achievement.AchievementProgressDto;

import java.util.List;

public interface AchievementProgressService {
    List<AchievementProgressDto> getAchievementProgressesByUserId(long userId);
}
