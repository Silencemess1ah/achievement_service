package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.filter.AchievementFilter;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final List<AchievementFilter> achievementFilters;
    private final AchievementMapper achievementMapper;

    public List<AchievementDto> getAchievements(AchievementFilterDto filters) {
        Iterable<Achievement> achievements = achievementRepository.findAll();
        Stream<Achievement> achievementStream = StreamSupport.stream(achievements.spliterator(), false);
        return achievementFilters.stream()
                .filter(filter -> filter.isApplicable(filters))
                .flatMap(filter -> filter.apply(achievementStream, filters))
                .distinct()
                .map(achievementMapper::toDto)
                .toList();
    }
}
