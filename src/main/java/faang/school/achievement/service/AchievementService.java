package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.filter.AchievementFilter;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final String ACHIEVEMENT_NOT_FOUND_MSG = "Could not find achievement with ID: %s";

    private final AchievementMapper achievementMapper;
    private final AchievementProgressMapper achievementProgressMapper;
    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;
    private final List<AchievementFilter> achievementFilters;

    @Transactional(readOnly = true)
    public List<AchievementDto> getAllAchievements(AchievementFilterDto achievementFilterDto) {
        Stream<Achievement> matchedAchievements = achievementRepository.findAll().stream();
        for (AchievementFilter achievementFilter : achievementFilters) {
            matchedAchievements = achievementFilter.filter(matchedAchievements, achievementFilterDto);
        }
        return matchedAchievements.map(achievementMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<AchievementDto> getAchievementsByUserId(long userId) {
        return userAchievementRepository.findByUserId(userId).stream()
                .map(UserAchievement::getAchievement)
                .map(achievementMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public AchievementDto getAchievementById(long achievementId) {
        Achievement achievementById = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ACHIEVEMENT_NOT_FOUND_MSG, achievementId)));
        return achievementMapper.toDto(achievementById);
    }

    @Transactional(readOnly = true)
    public List<AchievementProgressDto> getUserNotAttainedAchievements(long userId) {
        List<Achievement> userAttainedAchievements = userAchievementRepository.findByUserId(userId)
                .stream()
                .map(UserAchievement::getAchievement)
                .toList();
        return achievementProgressRepository.findByUserId(userId)
                .stream()
                .filter(achievementProgress -> !userAttainedAchievements.contains(achievementProgress.getAchievement()))
                .map(achievementProgressMapper::toDto)
                .toList();
    }
}
