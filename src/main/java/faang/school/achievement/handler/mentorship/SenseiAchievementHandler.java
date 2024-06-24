package faang.school.achievement.handler.mentorship;

import faang.school.achievement.dto.achievement.AchievementDto;
import faang.school.achievement.event.mentorship.MentorshipStartEvent;
import faang.school.achievement.handler.EventHandler;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.achievement.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SenseiAchievementHandler implements EventHandler<MentorshipStartEvent> {

    @Value("${achievements.sensei.name}")
    private String achievement;

    private final AchievementService achievementService;
    private final AchievementMapper achievementMapper;

    @Async("achievementExecutorService")
    public void handle(MentorshipStartEvent event) {
        AchievementDto achievementDto = achievementService.getAchievementByTitle(this.achievement);
        Achievement achievement = achievementMapper.toEntity(achievementDto);

        if (!achievementService.hasAchievement(event.getMentorId(), achievement.getId())) {
            achievementService.createProgressIfNecessary(event.getMentorId(), achievement.getId());
        }

        AchievementProgress progress = achievementService.getProgress(event.getMentorId(), achievement.getId());
        progress.setCurrentPoints(progress.getCurrentPoints() + 1);

        if (progress.getCurrentPoints() >= achievement.getPoints()) {
            achievementService.giveAchievement(event.getMentorId(), achievement);
        }
    }
}
