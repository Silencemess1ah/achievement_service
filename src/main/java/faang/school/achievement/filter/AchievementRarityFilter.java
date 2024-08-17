package faang.school.achievement.filter;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;

import java.util.stream.Stream;

public class AchievementRarityFilter implements AchievementFilter {
    @Override
    public boolean isApplicable(AchievementFilterDto filters) {
        return filters.getRarity() != null;
    }

    @Override
    public Stream<Achievement> apply(Stream<Achievement> achievements, AchievementFilterDto filters) {
        return achievements.filter(achievement ->
                achievement.getRarity().equals(filters.getRarity()));
    }
}
