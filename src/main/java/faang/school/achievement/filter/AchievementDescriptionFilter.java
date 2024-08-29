package faang.school.achievement.filter;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;

import java.util.stream.Stream;

public class AchievementDescriptionFilter implements AchievementFilter {
    @Override
    public boolean isApplicable(AchievementFilterDto filters) {
        return filters.getDescriptionPrefix() != null && !filters.getDescriptionPrefix().isBlank();
    }

    @Override
    public Stream<Achievement> apply(Stream<Achievement> achievements, AchievementFilterDto filters) {
        return achievements.filter(achievement ->
                achievement.getDescription().startsWith(filters.getDescriptionPrefix()));
    }
}
