package faang.school.achievement.service.filter;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;

public interface AchievementFilter {
    boolean isApplicable(AchievementFilterDto filter);

    boolean apply(Achievement achievement, AchievementFilterDto filter);
}
