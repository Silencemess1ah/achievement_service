package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.filter.AchievementFilter;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementMapper mapper;
    private final List<AchievementFilter> achievementFilters;

    public AchievementDto getAchievement(Long achievementId) {
        Achievement achievement = achievementRepository
                .findById(achievementId).orElseThrow(() -> new EntityNotFoundException("Достижения с таким id не существует."));
        return mapper.toAchievementDto(achievement);
    }

    public List<AchievementDto> getUserAchievements(Long userId) {
        return userAchievementRepository.findByUserId(userId).stream()
                .map(userAchievement -> mapper.toAchievementDto(userAchievement.getAchievement()))
                .toList();
    }

    public List<AchievementProgressDto> getAchievementsProgress(Long userId) {
        return achievementProgressRepository.findByUserId(userId).stream()
                .map(mapper::toAchievementProgressDto)
                .toList();
    }

    public List<AchievementDto> getAchievements(AchievementFilterDto filters) {
        List<AchievementFilter> actualFilters = achievementFilters.stream()
                .filter(filter -> filter.isApplicable(filters))
                .toList();

        return StreamSupport.stream(achievementRepository.findAll().spliterator(), false)
                .filter(achievement -> actualFilters.stream()
                        .allMatch(filter -> filter.test(achievement, filters)))
                .map(mapper::toAchievementDto)
                .toList();
    }

    public boolean hasAchievement(long userId, long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    public void createProgressIfNecessary(long followeeId, long achievementId) {
        achievementProgressRepository.createProgressIfNecessary(followeeId, achievementId);
    }

    public AchievementProgress getProgress(long followeeId, long achievementId) {
        return achievementProgressRepository
                .findByUserIdAndAchievementId(followeeId, achievementId)
                .orElseThrow(() -> new EntityNotFoundException("Achievement progress by user ID \"%d\" and achievement ID \"%d\" not found"
                        .formatted(followeeId, achievementId)));
    }

    public AchievementProgress saveProgress(AchievementProgress progress) {
        return achievementProgressRepository.save(progress);
    }

    public void giveAchievement(long userId, Achievement achievement) {
        UserAchievement userAchievement = UserAchievement.builder()
                .achievement(achievement)
                .userId(userId)
                .build();
        userAchievementRepository.save(userAchievement);
    }
}
