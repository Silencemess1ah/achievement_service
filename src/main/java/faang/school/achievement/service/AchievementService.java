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
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
@Builder
public class AchievementService {

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
        Achievement achievementById = getOrThrowException(achievementId);
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

    private Achievement getOrThrowException(long achievementId) {
        Optional<Achievement> achievement = achievementRepository.findById(achievementId);
        return achievement.orElseThrow(() -> {
            String errMessage = String.format("Could not find Achievement with ID: %d", achievementId);
            EntityNotFoundException exception = new EntityNotFoundException(errMessage);
            log.error(errMessage, exception);
            return exception;
        });
    }
}
