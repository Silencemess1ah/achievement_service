package faang.school.achievement.service.achievement.filter;

import faang.school.achievement.dto.achievement.AchievementFilterDto;
import faang.school.achievement.model.Achievement;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public interface AchievementFilter {
    boolean isApplicable(AchievementFilterDto filter);

    Stream<Achievement> apply(Stream<Achievement> achievements, AchievementFilterDto filter);
}
