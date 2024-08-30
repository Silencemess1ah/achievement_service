package faang.school.achievement.service;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.config.context.UserContext;
import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementEventDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.exception.DataNotFoundException;
import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.filter.achievement.AchievementFilter;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.publisher.achievement.AchievementPublisher;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
@Builder
public class AchievementService {
    private final UserContext userContext;
    private final AchievementCache achievementCache;
    private final AchievementMapper achievementMapper;
    private final UserAchievementMapper userAchievementMapper;
    private final AchievementProgressMapper achievementProgressMapper;
    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;
    private final List<AchievementFilter> achievementFilters;
    private final AchievementPublisher achievementPublisher;

    @Transactional
    public void grantAchievement(long achievementId) {
        long userId = userContext.getUserId();
        Achievement achievement = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find achievement with ID: %d"
                        .formatted(achievementId)));

        AchievementEventDto event = new AchievementEventDto(userId, achievementId);
        achievementPublisher.publish(event);

        UserAchievement userAchievement = UserAchievement.builder()
                .userId(userId)
                .achievement(achievement)
                .build();
        userAchievementRepository.save(userAchievement);
    }

    @Transactional(readOnly = true)
    public List<AchievementDto> getAchievementsByFilter(AchievementFilterDto achievementFilterDto,
                                                        int offset, int limit, String sortField) {
        Stream<Achievement> matchedAchievements = achievementRepository
                .findAll(PageRequest.of(offset, limit, Sort.by(sortField))).stream();
        for (AchievementFilter achievementFilter : achievementFilters) {
            matchedAchievements = achievementFilter.filter(matchedAchievements, achievementFilterDto);
        }
        return matchedAchievements.map(achievementMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<UserAchievementDto> getAchievementsByUserId() {
        long userId = userContext.getUserId();
        List<UserAchievement> userAchievements = userAchievementRepository.findByUserId(userId);
        return userAchievements.stream()
                .map(userAchievementMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public AchievementDto getAchievementById(long id) {
        Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Achievement with ID: %d not found", id)));
        return achievementMapper.toDto(achievement);
    }

    @Transactional(readOnly = true)
    public List<AchievementProgressDto> getUserNotAttainedAchievements() {
        long userId = userContext.getUserId();
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

    @Transactional(readOnly = true)
    public List<AchievementProgressDto> getAchievementProgressByUserId() {
        long userId = userContext.getUserId();
        List<AchievementProgress> achievementProgresses = achievementProgressRepository.findByUserId(userId);
        return achievementProgresses.stream()
                .map(achievementProgressMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public AchievementDto getAchievementByTitle(String title) {
        Achievement cachedAchievement = achievementCache.getAchievementByTitle(title);

        return achievementMapper.toDto(cachedAchievement);
    }

    public boolean hasAchievement(long userId, long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Transactional
    public void createProgressIfNecessary(long userId, long achievementId) {
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
    }

    public AchievementProgress getProgress(long userId, long achievementId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseThrow(() -> new DataNotFoundException("Achievement progress not found"));
    }

    @Transactional
    public void giveAchievement(Achievement achievement, long userId) {
        UserAchievement userAchievement = UserAchievement.builder()
                .achievement(achievement)
                .userId(userId)
                .build();

        userAchievementRepository.save(userAchievement);
    }

    public AchievementProgress incrementAchievementProgress(long userId, long achievementId) {
        AchievementProgress achievementProgress = getProgress(userId, achievementId);
        achievementProgress.increment();
        return achievementProgressRepository.save(achievementProgress);
    }
}