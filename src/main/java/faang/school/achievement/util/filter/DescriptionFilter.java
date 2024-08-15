package faang.school.achievement.util.filter;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;

import java.util.stream.Stream;

public class DescriptionFilter implements AchievementFilter {

    @Override
    public boolean isApplicable(AchievementFilterDto filters) {
        return filters.getDescriptionPattern() != null;
    }

    @Override
    public Stream<Achievement> apply(Stream<Achievement> achievementStream, AchievementFilterDto filters) {
        return achievementStream.filter(achievement -> achievement.getDescription().contains(filters.getDescriptionPattern()));
    }
}
