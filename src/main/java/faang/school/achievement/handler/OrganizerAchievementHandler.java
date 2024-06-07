package faang.school.achievement.handler;

import faang.school.achievement.event.InviteSentEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrganizerAchievementHandler implements EventHandler<InviteSentEvent> {

    private static final String ACHIEVEMENT = "COLLECTOR";
    private final AchievementService achievementService;

    @Async("organizerAchievementExecutorService")
    public void handle(InviteSentEvent event) {

        Achievement achievement = achievementService.getAchievementByTitle(ACHIEVEMENT);

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
