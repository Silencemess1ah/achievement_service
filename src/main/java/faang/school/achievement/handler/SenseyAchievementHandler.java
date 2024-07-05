package faang.school.achievement.handler;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.event.MentorshipStartEvent;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SenseyAchievementHandler implements EventHandler {

    private String achievement;

    private final AchievementService achievementService;

    private final AchievementMapper achievementMapper;

    public void handleEvent(MentorshipStartEvent event) {
        AchievementDto achievementDto = achievementService.getAchievementByTitle(achievement);
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
