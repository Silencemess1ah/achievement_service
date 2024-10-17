package faang.school.achievement.handler;

import faang.school.achievement.dto.PostEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class WriterAchievementHandler {
    private final AchievementService achievementService;
    private final int achievementPoints;

    @Async("treadPool")
    @Transactional
    public void computeAchievement(PostEventDto postEventDto) {
        Achievement achievement = achievementService.findAchievement("WRITER");
        Long userId = postEventDto.getAuthorId();

        if (!hasProgress(achievement, userId)) {
            AchievementProgress progress = createProgress(achievement, userId);
            achievementService.saveAchievementProgress(progress);

        } else if(!hasAchievement(achievement, userId) && hasProgress(achievement, userId)) {
            AchievementProgress progress = getProgress(achievement, userId);
            progress.increment();

            if (progress.getCurrentPoints() < achievementPoints) {
                achievementService.saveAchievementProgress(progress);
            } else {
                UserAchievement userAchievement = createUserAchievement(progress);
                achievementService.saveUserAchievement(userAchievement);
            }
        }
    }


    private boolean hasAchievement(Achievement achievement, Long userId) {
        List<Long> userIdsWithAchievement = achievement.getUserAchievements().stream()
                .map(UserAchievement::getUserId)
                .toList();
        return userIdsWithAchievement.contains(userId);
    }

    private boolean hasProgress(Achievement achievement, Long userId) {
        List<Long> userIdsWithProgress = achievement.getProgresses().stream()
                .map(AchievementProgress::getUserId)
                .toList();
        return userIdsWithProgress.contains(userId);
    }
    
    private AchievementProgress getProgress(Achievement achievement, Long userId) {
        return achievement.getProgresses().stream()
                .filter(prog -> prog.getUserId() == userId)
                .findFirst()
                .orElseThrow();
    }

    private AchievementProgress createProgress(Achievement achievement, Long userId) {
        return AchievementProgress.builder()
                .achievement(achievement)
                .userId(userId)
                .currentPoints(1L)
                .version(0L)
                .build();
    }

    private static UserAchievement createUserAchievement(AchievementProgress progress) {
        return UserAchievement.builder()
                .achievement(progress.getAchievement())
                .userId(progress.getUserId())
                .build();
    }

}
