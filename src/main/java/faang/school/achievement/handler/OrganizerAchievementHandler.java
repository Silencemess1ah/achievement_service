package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.dto.achievement.AchievementDto;
import faang.school.achievement.event.InviteSentEvent;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
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
    private final AchievementProgressService achievementProgressService;
    private final UserAchievementService userAchievementService;
    private final AchievementCache achievementCache;
    private final AchievementMapper achievementMapper;

    @Override
    @Async("organizerAchievementExecutorService")
    public void handle(InviteSentEvent event) {

        Achievement achievement = achievementCache.get(achievementName);

        if (!userAchievementService.hasAchievement(event.getUserId(), achievement.getId())) {
            achievementProgressService.createProgressIfNecessary(event.getUserId(), achievement.getId());
        }

        long points = achievementProgressService.incrementAndGetProgress(event.getUserId(), achievement.getId());

        AchievementDto achievementDto = achievementMapper.toDto(achievement);
        if (points >= achievement.getPoints()) {
            userAchievementService.giveAchievement(event.getUserId(), achievementDto);
        }
    }
}
