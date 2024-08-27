package faang.school.achievement.handler;

import faang.school.achievement.event.TeamEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementProgressService;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public abstract class TeamEventHandler extends AbstractEventHandler<TeamEvent> {


    public TeamEventHandler(AchievementService achievementService,
                            AchievementProgressService achievementProgressService,
                            UserAchievementService userAchievementService) {
        super(achievementService, achievementProgressService, userAchievementService);
    }

        @Async("asyncExecutor")
    public void checkAchievement(TeamEvent event){
        Achievement achievement = achievementService.getAchievementFromCache(getAchievementName());
        if (!userAchievementService.hasAchievement(event.getAuthorId(),achievement.getId())) {
            achievementProgressService.createProgressIfNecessary(event.getAuthorId(),achievement.getId());
            AchievementProgress progress = achievementProgressService.getProgress(event.getAuthorId(), achievement.getId());
            progress.increment();
            achievementProgressService.saveAchievementProgress(progress);
            if (progress.getCurrentPoints() >= achievement.getPoints()){
                userAchievementService.giveAchievement(event.getAuthorId(),achievement);
            }
        }
    }

    @Override
    public abstract String getAchievementName();
}
