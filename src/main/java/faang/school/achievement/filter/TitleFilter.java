package faang.school.achievement.filter;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;

import java.util.stream.Stream;

public class TitleFilter implements AchievementFilter {
    @Override
    public boolean isApplicable(AchievementFilterDto filters) {
        return filters.getTitle() != null;
    }

    @Override
    public Stream<Achievement> apply(Stream<Achievement> achievementStream, AchievementFilterDto filters) {
        return achievementStream.filter(achievement -> achievement.getTitle().equals(filters.getTitle()));
    }
}
