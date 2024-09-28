package faang.school.achievement.service.filter;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;

import java.util.List;
import java.util.stream.Stream;

public interface AchievementFilter {

    boolean isApplicable(AchievementFilterDto filter);

    Stream<Achievement> apply(List<Achievement> achievements, AchievementFilterDto filter);
}
