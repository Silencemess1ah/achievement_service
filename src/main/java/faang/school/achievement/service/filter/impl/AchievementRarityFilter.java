package faang.school.achievement.service.filter.impl;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.service.filter.AchievementFilter;

import java.util.List;
import java.util.stream.Stream;

public class AchievementRarityFilter implements AchievementFilter {

    @Override
    public boolean isApplicable(AchievementFilterDto filter) {
        return filter.getRarity() != null;
    }

    @Override
    public Stream<Achievement> apply(List<Achievement> achievements, AchievementFilterDto filter) {
        return achievements.stream()
                .filter(achievement -> achievement.getRarity().equals(filter.getRarity()));
    }
}
