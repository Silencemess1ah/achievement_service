package faang.school.achievement.service.achievement.filter;

import faang.school.achievement.dto.achievement.AchievementFilterDto;
import faang.school.achievement.filter.achievement.AchievementFilter;
import faang.school.achievement.model.Achievement;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AchievementsFilterServiceImpl implements AchievementFilterService {

    private final List<AchievementFilter> filters;

    @Override
    public Stream<Achievement> applyFilters(Stream<Achievement> achievements, @NonNull AchievementFilterDto filtersDto) {

        return filters.stream()
                .filter(achievementFilter -> achievementFilter.isAcceptable(filtersDto))
                .flatMap(achievementFilter -> achievementFilter.apply(achievements, filtersDto));
    }
}
