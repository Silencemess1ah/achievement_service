package faang.school.achievement.handler;

import faang.school.achievement.dto.achievement.AchievementDto;
import faang.school.achievement.event.InviteSentEvent;
import faang.school.achievement.service.achievement.AchievementService;
import faang.school.achievement.service.achievement_progress.AchievementProgressService;
import faang.school.achievement.service.user_achievement.UserAchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrganizerAchievementHandler implements EventHandler<InviteSentEvent> {

    @Value("${achievements.organizer.name}")
    private String achievementName;
    private final AchievementService achievementService;
    private final AchievementProgressService achievementProgressService;
    private final UserAchievementService userAchievementService;

    @Override
    @Async("organizerAchievementExecutorService")
    public void handle(InviteSentEvent event) {

        AchievementDto achievement = achievementService.getAchievementByTitle(achievementName);

        if (!userAchievementService.hasAchievement(event.getUserId(), achievement.getId())) {
            achievementProgressService.createProgressIfNecessary(event.getUserId(), achievement.getId());
        }

        long points = achievementProgressService.incrementAndGetProgress(event.getUserId(), achievement.getId());

        if (points >= achievement.getPoints()) {
            userAchievementService.giveAchievement(event.getUserId(), achievement);
        }
    }
}
