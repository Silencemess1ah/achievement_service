package faang.school.achievement.util.filter;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;

import java.util.stream.Stream;

public interface AchievementFilter {

    boolean isApplicable(AchievementFilterDto filters);

    Stream<Achievement> apply(Stream<Achievement> achievementStream, AchievementFilterDto filters);
}
