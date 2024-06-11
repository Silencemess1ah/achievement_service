package faang.school.achievement.handler;

import faang.school.achievement.event.InviteSentEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
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

    @Async("organizerAchievementExecutorService")
    public void handle(InviteSentEvent event) {

        Achievement achievement = achievementService.getAchievementByTitle(achievementName);

        if (!achievementService.hasAchievement(event.getUserId(), achievement.getId())) {
            achievementService.createProgressIfNecessary(event.getUserId(), achievement.getId());
        }

        AchievementProgress progress = achievementService.getProgress(event.getUserId(), achievement.getId());

        progress.setCurrentPoints(progress.getCurrentPoints() + 1);

        if (progress.getCurrentPoints() >= achievement.getPoints()) {
            achievementService.giveAchievement(event.getUserId(), achievement);
        }
    }
}
