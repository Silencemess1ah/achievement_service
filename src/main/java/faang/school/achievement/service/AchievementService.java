package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;
    private final AchievementMapper achievementMapper;
    private final List<AchievementFilter> achievementsFilter;

    public boolean hasAchievement(long userId, long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Transactional
    public void createProgressIfNecessary(long userId, long achievementId) {
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
    }

    public AchievementProgress getProgress(long userId, long achievementId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                String.format("Achievement progress not found by id: %s", achievementId)
                        ));
    }

    public AchievementProgress saveAchievementProgress(AchievementProgress achievementProgress) {
        return achievementProgressRepository.save(achievementProgress);
    }

    @Transactional
    public void giveAchievement(long userId, Achievement achievement) {
        UserAchievement userAchievement = UserAchievement.builder()
                .userId(userId)
                .achievement(achievement)
                .build();

        userAchievementRepository.save(userAchievement);
    }

    @Transactional
    public List<AchievementDto> getAllAchievement(AchievementFilterDto achievementFilterDto) {
        if (achievementFilterDto == null) {
            log.error("achievement filter is null");
            throw new IllegalArgumentException("achievement filter is null");
        }

        Iterable<Achievement> achievements = achievementRepository.findAll();
        Stream<Achievement> achievementsStream = StreamSupport.stream(achievements.spliterator(), false);

        return achievementsFilter.stream()
                .filter(achievementFilter -> achievementFilter.isApplicable(achievementFilterDto))
                .reduce(achievementsStream, (cumulativeStream, achievementsFilter) ->
                        achievementsFilter.apply(cumulativeStream, achievementFilterDto), Stream::concat)
                .map(achievementMapper::toDto)
                .toList();
    }

    @Transactional
    public List<AchievementDto> getAllAchievementForUser(Long userId) {
        List<Achievement> achievements = achievementRepository.findAllAchievementForUser(userId);
        return achievements.stream().map(achievementMapper::toDto).toList();
    }

    @Transactional
    public List<AchievementDto> getAchievement(Long achievementId) {
        Optional<Achievement> achievements = achievementRepository.findById(achievementId);
        if (!achievements.isEmpty()) {
            return achievements.stream().map(achievementMapper::toDto).toList();
        }
        return new ArrayList<AchievementDto>();
    }

    @Transactional
    public List<AchievementDto> getNoAchievement(Long userId) {
        List<AchievementProgress> achievementProgresses = achievementProgressRepository.findByUserId(userId);
        return achievementProgresses.stream()
                .map(AchievementProgress::getAchievement)
                .map(achievementMapper::toDto)
                .toList();
    }
}