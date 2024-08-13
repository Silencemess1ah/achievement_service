package faang.school.achievement.filter.achievement;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;

import java.util.stream.Stream;

/**
 * @author Evgenii Malkov
 */
public interface AchievementFilter {

    boolean isApplicable(AchievementFilterDto filter);

    Stream<Achievement> apply(Stream<Achievement> achievementStream, AchievementFilterDto filter);
}
