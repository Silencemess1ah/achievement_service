package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementReadDto;
import faang.school.achievement.mapper.AchievementToAchievementReadDtoMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@CacheConfig(cacheNames = "achievement")
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;
    private final AchievementToAchievementReadDtoMapper achievementToAchievementReadDtoMapper;

    @Cacheable(key = "'allAchievements'")
    public List<AchievementReadDto> findAll() {
        return achievementRepository.findAll().stream()
                .map(achievementToAchievementReadDtoMapper::map)
                .toList();
    }

    public AchievementReadDto findById(Long id) {
        return achievementRepository.findById(id)
                .map(achievementToAchievementReadDtoMapper::map)
                .orElseThrow(() -> new NoSuchElementException("No achievement found with id: " + id));
    }

    public Achievement findByTitle(String title) {
        return achievementRepository.findByTitle(title)
                .orElseThrow(() -> new NoSuchElementException("No achievement found with title: " + title));
    }

    public boolean hasAchievement(Long userId, Long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    public AchievementProgress getProgress(long userId, long achievementId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseThrow(() -> {
                    String errorMessage = String.format("No achievement_progress found for userId %d and achievementId %d", userId, achievementId);
                    log.error(errorMessage);
                    return new NoSuchElementException(errorMessage);
                });
    }

    @Transactional
    public UserAchievement giveAchievement(long userId, Achievement achievement) {
        return userAchievementRepository.save(UserAchievement.builder()
                .userId(userId)
                .achievement(achievement)
                .build());
    }

    @Transactional
    public void saveAndFlush(AchievementProgress progress) {
        achievementProgressRepository.saveAndFlush(progress);
    }

    @Transactional
    public void createProgressIfNecessary(long userId, long achievementId) {
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
    }
}
