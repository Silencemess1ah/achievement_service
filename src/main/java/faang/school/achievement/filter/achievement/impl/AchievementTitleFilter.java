package faang.school.achievement.filter.achievement.impl;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.filter.achievement.AchievementFilter;
import faang.school.achievement.model.Achievement;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Evgenii Malkov
 */
@Component
public class AchievementTitleFilter implements AchievementFilter {

    @Override
    public boolean isApplicable(AchievementFilterDto filter) {
        return filter.getTitle() != null && !filter.getTitle().isBlank();
    }

    @Override
    public Stream<Achievement> apply(List<Achievement> achievements, AchievementFilterDto filter) {
        return achievements.filter((achieve) -> achieve.getTitle().toLowerCase().contains(filter.getTitle().toLowerCase()));
    }
}
