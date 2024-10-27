package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.AchievementTitle;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.filter.AchievementFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;
    private final List<AchievementFilter> achievementFilters;

    @Transactional(readOnly = true)
    public boolean hasAchievement(Long userId, AchievementTitle title) {
        return userAchievementRepository.hasAchievement(userId, title);
    }

    @Transactional
    public void updateProgress(Long userId, AchievementTitle title, Long requiredPoints) {
        AchievementProgress progress = achievementProgressRepository.getProgress(userId, title)
                .orElse(createProgress(userId, title));
        progress.increment();
        if (progress.getCurrentPoints() < requiredPoints) {
            achievementProgressRepository.save(progress);
            log.info("User {} add point to {} achievement", userId, title);
        } else {
            UserAchievement userAchievement = createUserAchievement(progress);
            userAchievementRepository.save(userAchievement);
            log.info("User {} get achievement {}", userId, title);
        }
    }

    @Transactional(readOnly = true)
    public List<Achievement> getAchievementByFilters(AchievementFilterDto filter) {
        log.info("Get achievements by filters: " + filter);
        return achievementRepository.findAll()
                .stream()
                .filter(events -> isAllMatch(filter, events))
                .toList();
    }

    @Transactional(readOnly = true)
    public Achievement getAchievementById(Long id) {
        log.info("Get achievement with ID: " + id);
        return achievementRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Achievement with ID: " + id + " not found.")
        );
    }

    @Transactional(readOnly = true)
    public List<UserAchievement> getUserAchievements(long userId) {
        log.info("Get achievements for user with ID: " + userId);
        return userAchievementRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<AchievementProgress> getUserProgress(long userId) {
        log.info("Get progress for user with ID: " + userId);
        return achievementProgressRepository.findByUserId(userId);
    }

    private AchievementProgress createProgress(Long userId, AchievementTitle title) {
        Achievement achievement = achievementRepository.findByTitle(title).orElseThrow();
        return AchievementProgress.builder()
                .achievement(achievement)
                .userId(userId)
                .currentPoints(0)
                .version(0)
                .build();
    }

    private UserAchievement createUserAchievement(AchievementProgress progress) {
        return UserAchievement.builder()
                .achievement(progress.getAchievement())
                .userId(progress.getUserId())
                .build();
    }

    private boolean isAllMatch(AchievementFilterDto filterDto, Achievement achievement) {
        return achievementFilters.stream()
                .filter(filter -> filter.isApplicable(filterDto))
                .allMatch(filter -> filter.apply(achievement, filterDto));
    }
}