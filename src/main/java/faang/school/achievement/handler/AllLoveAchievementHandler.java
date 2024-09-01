package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.AchievementEventDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.event.LikeEvent;
import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.publisher.achievement.AchievementPublisher;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AllLoveAchievementHandler {
    private static final String ACHIEVEMENT_TITLE = "ALL LOVE";
    private static final int ACHIEVEMENT_REQUIRED_LIKES = 4;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;
    private final AchievementRepository achievementRepository;
    private final AchievementPublisher achievementPublisher;
    private final AchievementCache achievementCache;
    private final AchievementService achievementService;
    private final UserAchievementMapper userAchievementMapper;
    private final AchievementProgressMapper achievementProgressMapper;

    @Transactional
    public void handler(LikeEvent likeEvent) {
        long userId = likeEvent.getPostAuthorId();
        long achievementId;
        Achievement allLoveAchievement = achievementCache.getAchievementByTitle(ACHIEVEMENT_TITLE).get();
        achievementId = allLoveAchievement.getId();

        if (likeEvent.getPostAuthorId() != null &&
                hasAchievement(likeEvent.getPostAuthorId(), allLoveAchievement.getId())) {
            log.info("user with id: " + userId + " already has achievement: " + ACHIEVEMENT_TITLE);
            return;
        }

        AchievementProgress achievementProgress = createProgressIfNeccessary(userId, achievementId);

        achievementProgress.increment();
        achievementProgressRepository.save(achievementProgress);
        if (achievementProgress.getCurrentPoints() >= ACHIEVEMENT_REQUIRED_LIKES) {
            grantAchievement(userId, achievementId);
        }

    }

    private boolean hasAchievement(long userId, long achievementId) {
        return getAchievementsByUserId(userId).stream()
                .anyMatch(userAchievementDto -> userAchievementDto.achievementId() == achievementId);
    }

    public List<UserAchievementDto> getAchievementsByUserId(long userId) {
        List<UserAchievement> userAchievements = userAchievementRepository.findByUserId(userId);
        return userAchievements.stream()
                .map(userAchievementMapper::toDto)
                .toList();
    }

    protected AchievementProgress createProgressIfNeccessary(long userId, long achievementId) {
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
        AchievementProgress achievementProgress = achievementService.getProgress(userId, achievementId);
        return achievementProgress;
    }

    public void grantAchievement(long userId, long achievementId) {
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

//    public boolean reachAchievementChecker(long userId, long achievementId) {
//
//    }
}
