package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.filter.AchievementFilter;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
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
    private final UserAchievementRepository userAchievementRepository;

    public List<AchievementDto> getAchievements(AchievementFilterDto filters) {
        Iterable<Achievement> achievements = achievementRepository.findAll();
        Stream<Achievement> achievementStream = StreamSupport.stream(achievements.spliterator(), false);
        return achievementFilters.stream()
                .filter(filter -> filter.isApplicable(filters))
                .flatMap(filter -> filter.apply(achievementStream, filters))
                .map(achievementMapper::toDto)
                .toList();
    }

    public List<AchievementDto> getUserAchievements(Long userId) {
        return userAchievementRepository.findByUserId(userId).stream()
                .map(UserAchievement::getAchievement)
                .map(achievementMapper::toDto)
                .toList();
    }

    public AchievementDto getAchievement(Long achievementId) {
        return achievementRepository.findById(achievementId)
                .map(achievementMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Achievement not found"));
    }

    public List<AchievementProgressDto> getMissedUserAchievements(Long userId) {
        return null;
    }
}
