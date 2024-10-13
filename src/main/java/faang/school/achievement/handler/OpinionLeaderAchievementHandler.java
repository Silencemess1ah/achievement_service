package faang.school.achievement.handler;

import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.PostEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.AchievementServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpinionLeaderAchievementHandler implements EventHandler {
    private final AchievementService achievementService;

    @Override
    @Async
    public void handle(PostEvent event) {
        Achievement achievement = new Achievement();//тут надо получить достижение из кеша по названию
        if (!achievementService.hasAchievement(event.getAuthorId(), achievement.getId())) {
            achievementService.createProgressIfNecessary(event.getAuthorId(), achievement.getId());
            AchievementProgressDto achievementProgressDto = achievementService.getProgress(event.getAuthorId(), achievement.getId());
            achievementProgressDto.setCurrentPoints(achievementProgressDto.getCurrentPoints() + 1);
            if (achievementProgressDto.getCurrentPoints() == achievement.getPoints()) {

            }
        }
    }
}
