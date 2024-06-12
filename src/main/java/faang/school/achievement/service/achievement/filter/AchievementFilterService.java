package faang.school.achievement.service.achievement.filter;

import faang.school.achievement.dto.achievement.AchievementFilterDto;
import faang.school.achievement.model.Achievement;

import java.util.stream.Stream;

public interface AchievementFilterService {
    Stream<Achievement> applyFilters(Stream<Achievement> achievements, AchievementFilterDto filters);
}
