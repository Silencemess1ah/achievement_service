package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.filter.AchievementFilter;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class AchievementService {
    private static final String MESSAGE_DOES_NOT_HAVE_ACHIEVEMENT = "The user does not have such an achievement";
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

    public boolean hasAchievement(Long userId, Long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    public void createProgressIfNecessary(Long userId, Long achievementId) {
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
    }

    public AchievementProgressDto getProgress(Long userId, Long achievementId) {
        return mapper.toAchievementProgressDto(
                achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
                        .orElseThrow(() -> new RuntimeException(MESSAGE_DOES_NOT_HAVE_ACHIEVEMENT)));
    }

    public void giveAchievement(Long userId, Achievement achievement) {
        userAchievementRepository.save(
                UserAchievement.builder()
                        .userId(userId)
                        .achievement(achievement)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build());
    }
}
