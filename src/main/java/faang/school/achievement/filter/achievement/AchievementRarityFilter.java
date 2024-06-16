package faang.school.achievement.filter.achievement;

import faang.school.achievement.dto.achievement.AchievementFilterDto;
import faang.school.achievement.model.Achievement;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class AchievementRarityFilter implements AchievementFilter {

    @Override
    public boolean isAcceptable(AchievementFilterDto filters) {
        return filters.getRarity() != null;
    }

    @Override
    public Stream<Achievement> apply(Stream<Achievement> achievements, AchievementFilterDto filters) {
        return achievements.filter(achievement -> achievement.getRarity() == filters.getRarity());
    }
}
