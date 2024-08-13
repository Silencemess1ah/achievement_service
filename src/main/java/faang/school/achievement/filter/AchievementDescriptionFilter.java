package faang.school.achievement.filter;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;

import java.util.stream.Stream;

public class AchievementDescriptionFilter implements AchievementFilter {
    @Override
    public boolean isApplicable(AchievementFilterDto achievementFilterDto) {
        return achievementFilterDto.getDescription() != null;
    }

    @Override
    public Stream<Achievement> apply(Stream<Achievement> achievements, AchievementFilterDto achievementFilterDto) {
        return achievements.filter(achievement ->
                achievementFilterDto.getDescription().equals(achievement.getDescription()));
    }
}