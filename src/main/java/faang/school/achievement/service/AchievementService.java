package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.filter.AchievementFilter;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.mapper.UserAchievementMapper;
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

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class AchievementService {
    private final UserAchievementRepository userAchievementRepository;
    private final UserAchievementMapper userAchievementMapper;
    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;
    private final AchievementProgressRepository achievementProgressRepository;
    private final AchievementProgressMapper achievementProgressMapper;
    private final List<AchievementFilter> filters;

    @Transactional(readOnly = true)
    public List<UserAchievementDto> getAllUserAchievements(long userId) {
        log.info("Getting all user (ID = {}) achievements ", userId);
        return userAchievementRepository.findByUserId(userId)
                .stream().map(userAchievementMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public AchievementDto getAchievementById(long id) {
        Achievement achievement = achievementRepository.findById(id).orElseThrow(() ->
        {
            String errorMessage = String.format("Achievement with ID = %d doesn't exist in the system", id);
            log.error(errorMessage);
            return new EntityNotFoundException(errorMessage);
        });
        return achievementMapper.toDto(achievement);
    }

    @Transactional(readOnly = true)
    public List<AchievementProgressDto> getAllUnreceivedUserAchievements(long userId) {
        return achievementProgressMapper.toDtoList(
                achievementProgressRepository.findByUserId(userId));
    }

    @Transactional(readOnly = true)
    public List<AchievementDto> getFilteredAchievements(AchievementFilterDto achievementFilterDto) {
        Stream<Achievement> achievementStream = StreamSupport.stream(achievementRepository.findAll().spliterator(), false);
        return filters.stream()
                .filter(filter -> filter.isApplicable(achievementFilterDto))
                .flatMap(filter -> filter.applyFilter(achievementStream, achievementFilterDto))
                .map(achievementMapper::toDto)
                .toList();
    }

    @Transactional
    public boolean hasAchievement(long userId, Achievement achievement) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievement.getId());
    }

    @Transactional
    public void createProgressIfNecessary(Long userId, Long achievementId) {
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
    }

    public AchievementProgress getAchievementProgress(long userId, Achievement achievement) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievement.getId()).orElseThrow(() -> {
            String errorMessage = "Couldn't find Achievement Progress entity. User ID = "
                    + userId + " Achievement ID = " + achievement.getId();
            log.error(errorMessage);
            return new EntityNotFoundException(errorMessage);
        });
    }

    public UserAchievement giveAchievement(Achievement achievement, AchievementProgress achievementProgress) {
        UserAchievement userAchievement = UserAchievement.builder().userId(achievementProgress.getUserId())
                .achievement(achievement)
                .build();
        return userAchievementRepository.save(userAchievement);
    }
}
