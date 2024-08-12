package faang.school.achievement.filter;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;

public interface AchievementFilter {
    boolean isApplicable(AchievementFilterDto filters);

    boolean test(Achievement entity, AchievementFilterDto filters);
}
