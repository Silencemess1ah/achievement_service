package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.AchievementEventDto;
import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.event.LikeEvent;
import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.publisher.AchievementEventDtoPublisher;
import faang.school.achievement.publisher.AchievementEventPublisher;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AllLoveAchievementHandler {
    private final AchievementEventDtoPublisher achievementEventDtoPublisher;
    @Value("${achievement-handler.all-love-achievement-handler.achievement-name}")
    private String achievement_title;

    @Value("${achievement-handler.all-love-achievement-handler.achievement-required-likes}")
    private int achievementRequiredLikes;

    private final UserAchievementRepository userAchievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;
    private final AchievementRepository achievementRepository;
    private final AchievementEventPublisher achievementEventPublisher;
    private final AchievementCache achievementCache;
    private final AchievementService achievementService;
    private final UserAchievementMapper userAchievementMapper;;

    @Transactional
    public void handle(LikeEvent likeEvent) {
        long userId = likeEvent.getAuthorPostId();
        long achievementId;
        Achievement allLoveAchievement = achievementCache.getAchievementByTitle(achievement_title);
        achievementId = allLoveAchievement.getId();

        if (hasAchievement(userId, allLoveAchievement.getId())) {
            log.info("user with id: " + userId + " already has achievement: " + achievement_title);
            return;
        }

        AchievementProgress achievementProgress = createProgressIfNeccessary(userId, achievementId);

        achievementProgress.increment();
        achievementProgressRepository.save(achievementProgress);
        if (achievementProgress.getCurrentPoints() >= achievementRequiredLikes) {
            grantAchievement(userId, achievementId);
        }

    }

    public List<UserAchievementDto> getAchievementsByUserId(long userId) {
        List<UserAchievement> userAchievements = userAchievementRepository.findByUserId(userId);
        return userAchievements.stream()
                .map(userAchievementMapper::toDto)
                .toList();
    }

    public void grantAchievement(long userId, long achievementId) {
        Achievement achievement = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find achievement with ID: %d"
                        .formatted(achievementId)));

        AchievementEventDto event = new AchievementEventDto(userId, achievementId);
        achievementEventDtoPublisher.publish(event);

        UserAchievement userAchievement = UserAchievement.builder()
                .userId(userId)
                .achievement(achievement)
                .build();
        userAchievementRepository.save(userAchievement);
    }

    private AchievementProgress createProgressIfNeccessary(long userId, long achievementId) {
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
        AchievementProgress achievementProgress = achievementService.getProgress(userId, achievementId);
        return achievementProgress;
    }

    private boolean hasAchievement(long userId, long achievementId) {
        return getAchievementsByUserId(userId).stream()
                .anyMatch(userAchievementDto -> userAchievementDto.achievementId() == achievementId);
    }
}
