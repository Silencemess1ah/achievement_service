package faang.school.achievement.handler;

import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.dto.AchievementRedisDto;
import faang.school.achievement.model.event.PostEvent;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpinionLeaderAchievementHandler implements EventHandler<PostEvent> {

//    private final AchievementCache achievementCache;
    private final AchievementService achievementService;
    private final String leaderTitle = "LEADER";

    @Override
    @Async("")
    public void handle(PostEvent event) {
        AchievementRedisDto achievementRedisDto = null;/*achievementCache.getAchievementCache(leaderTitle)*/;
        long userId = event.authorId();
        long achievementId = achievementRedisDto.getId();

        if(!achievementService.hasAchievement(userId, achievementId)){
            achievementService.createProgress(userId, achievementId);
            AchievementProgress progress = achievementService.getProgress(userId, achievementId);
            progress.increment();
            if(progress.getCurrentPoints() == achievementRedisDto.getPoints()){
                achievementService.giveAchievement(userId, achievementId);
            }
        }
    }
}
