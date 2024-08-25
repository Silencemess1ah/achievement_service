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
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementMapper mapper;
    private final List<AchievementFilter> achievementFilters;
    private final MessageSource messageSource;

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

        return achievementRepository.findAll().stream()
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

    public void giveAchievement(Long userId, Achievement achievement) {
        UserAchievement userAchievement = UserAchievement.builder()
                .userId(userId)
                .achievement(achievement)
                .build();
        userAchievementRepository.save(userAchievement);
    }

    public void deleteAchievementProgress(Long achievementProgressId) {
        achievementProgressRepository.deleteById(achievementProgressId);
    }

    public void saveProgress(AchievementProgress achievementProgress) {
        achievementProgressRepository.save(achievementProgress);
    }

    public AchievementProgress getProgress(Long userId, Long achievementId) {
        return getAchievementProgress(userId, achievementId);
    }

    private AchievementProgress getAchievementProgress(Long userId, Long achievementId) {
        System.out.println(messageSource.getMessage("exception.not_found_achievementProgress",
                new Object[]{userId, achievementId}, Locale.ENGLISH));
        System.out.println(messageSource.getMessage("exception.not_found_achievementProgress",
                new Object[]{userId, achievementId}, Locale.getDefault()));
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("exception.not_found_achievementProgress",
                        new Object[]{userId, achievementId}, Locale.getDefault())));
    }
}
