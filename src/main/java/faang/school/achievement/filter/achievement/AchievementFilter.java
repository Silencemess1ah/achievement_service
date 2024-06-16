package faang.school.achievement.filter.achievement;

import faang.school.achievement.dto.achievement.AchievementFilterDto;
import faang.school.achievement.model.Achievement;

import java.util.stream.Stream;

public interface AchievementFilter {

    boolean isAcceptable(AchievementFilterDto filters);

    Stream<Achievement> apply(Stream<Achievement> achievements, AchievementFilterDto filters);
}
