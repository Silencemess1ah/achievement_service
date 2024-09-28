package faang.school.achievement.filter.achievement;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * @author Evgenii Malkov
 */
@Component
public class AchievementDescriptionFilter implements AchievementFilter {
    @Override
    public boolean isApplicable(AchievementFilterDto filter) {
        return filter.getDescription() != null && !filter.getDescription().isBlank();
    }

    @Override
    public Stream<Achievement> apply(Stream<Achievement> achievements, AchievementFilterDto filter) {
        return achievements.filter((achieve) -> achieve.getDescription().toLowerCase()
                .contains(filter.getDescription().toLowerCase()));
    }
}
