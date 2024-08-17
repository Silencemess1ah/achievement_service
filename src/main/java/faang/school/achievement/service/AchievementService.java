package faang.school.achievement.service;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.filter.AchievementFilter;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final List<AchievementFilter> achievementFilters;
    private final AchievementMapper achievementMapper;
    private final AchievementProgressMapper achievementProgressMapper;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;
    private final AchievementCache achievementCache;

    public List<AchievementDto> getAchievements(AchievementFilterDto filters) {
        if (filters == null) {
            log.error("Achievement filter is null");
            throw new IllegalArgumentException("Achievement filter is null");
        }
        Stream<Achievement> achievementStream = achievementCache.getAll().stream();
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

    public List<AchievementProgressDto> getUnfinishedAchievements(Long userId) {
        List<Long> achievedIds = userAchievementRepository.findByUserId(userId)
                .stream()
                .map(UserAchievement::getAchievement)
                .map(Achievement::getId)
                .toList();
        List<AchievementProgress> progresses = achievementProgressRepository.findByUserId(userId);
        return progresses.stream()
                .filter(progress -> !achievedIds.contains(progress.getAchievement().getId()))
                .map(achievementProgressMapper::toDto)
                .toList();
    }
}
