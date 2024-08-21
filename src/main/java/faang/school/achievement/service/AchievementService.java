package faang.school.achievement.service;

import faang.school.achievement.config.context.UserContext;
import faang.school.achievement.dto.AchievementEventDto;
import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.publisher.achievement.AchievementPublisher;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.filter.achievement.AchievementFilter;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.cache.AchievementCache;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;
    private final AchievementCache achievementCache;
    private final AchievementMapper achievementMapper;
    private final UserAchievementMapper userAchievementMapper;
    private final AchievementProgressMapper achievementProgressMapper;
    private final List<AchievementFilter> achievementFilters;
    private final AchievementPublisher achievementPublisher;
    private final UserContext userContext;

    @Transactional
    public void grantAchievement(long achievementId) {
        long userId = userContext.getUserId();
        Achievement achievement = getAchievementFromRepository(achievementId);

        AchievementEventDto event = new AchievementEventDto(userId, achievementId);
        achievementPublisher.publish(event);

        UserAchievement userAchievement = UserAchievement.builder()
            .userId(userId)
            .achievement(achievement)
            .build();
        userAchievementRepository.save(userAchievement);
    }

    private Achievement getAchievementFromRepository(long achievementId) {
        return achievementRepository.findById(achievementId)
            .orElseThrow(() -> new EntityNotFoundException("Achievement with id: %d not found."
                .formatted(achievementId)));
    }

    @Transactional(readOnly = true)
    public List<AchievementDto> getAchievementsByFilter(AchievementFilterDto achievementFilterDto) {
        List<Achievement> achievements = achievementRepository.findAll();
        return achievementFilters.stream()
            .filter(achievementFilter -> achievementFilter.isApplicable(achievementFilterDto))
            .reduce(achievements.stream(), (stream, filter) -> filter.apply(stream, achievementFilterDto), Stream::concat)
            .map(achievementMapper::toDto)
            .toList();
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
    public List<AchievementProgressDto> getAchievementProgressByUserId() {
        long userId = userContext.getUserId();
        List<AchievementProgress> achievementProgresses = achievementProgressRepository.findByUserId(userId);
        return achievementProgresses.stream()
            .map(achievementProgressMapper::toDto)
            .toList();
    }
  
    @Transactional(readOnly = true)
    public AchievementDto getAchievementByTitle(String title) {
        Optional<Achievement> cachedAchievement = achievementCache.getAchievementByTitle(title);
        Achievement achievement = cachedAchievement.orElseGet(() -> achievementRepository.findByTitle(title)
            .orElseThrow(() -> new EntityNotFoundException("Achievement with title: %s not found."
                .formatted(title))));

        return achievementMapper.toDto(achievement);
    }

    @Transactional
    public boolean hasAchievement(long userId, long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Transactional
    public void createProgressIfNecessary(long userId, long achievementId) {
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
    }

    @Transactional
    public Optional<AchievementProgress> getProgress(long userId, long achievementId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId);
    }

    @Transactional
    public void giveAchievement(Achievement achievement, long userId) {
        UserAchievement userAchievement = UserAchievement.builder()
                .achievement(achievement)
                .userId(userId)
                .createdAt(LocalDateTime.now())
                .build();
        userAchievementRepository.save(userAchievement);
    }
}  
