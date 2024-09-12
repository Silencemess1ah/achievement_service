package faang.school.achievement.filter.achievement;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Evgenii Malkov
 */
public interface AchievementFilter {

    boolean isApplicable(AchievementFilterDto filter);

    Stream<Achievement> apply(List<Achievement> achievementStream, AchievementFilterDto filter);
}
