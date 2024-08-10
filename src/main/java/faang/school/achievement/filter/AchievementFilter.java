package faang.school.achievement.filter;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public interface AchievementFilter {
    boolean isApplicable(AchievementFilterDto achievementFilterDto);

    Stream<Achievement> apply(Stream<Achievement> achievements, AchievementFilterDto achievementFilterDto);
}
