package faang.school.achievement.handler;

import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.TeamEvent;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
@Async
public class ManagerAchievementHandler implements EventHandler<TeamEvent> {

    private static final String TITLE_OF_ACHIEVEMENT = "MANAGER";
    private final AchievementCache achievementCache;
    private final AchievementService achievementService;
    private final AchievementProgressRepository achievementProgressRepository;
    private final AchievementMapper mapper;

    public void reaction(TeamEvent teamEvent) {
        Long userId = teamEvent.getUserId();
        Achievement managerAchievement = achievementCache.get(TITLE_OF_ACHIEVEMENT);
        if (achievementService.hasAchievement(userId, managerAchievement.getId())) {
            return;
        }
        achievementService.createProgressIfNecessary(userId, managerAchievement.getId());
        AchievementProgressDto progressDto = achievementService.getProgress(userId, managerAchievement.getId());
        AchievementProgress progress = mapper.toAchievementProgress(progressDto);
        progress.increment();
        achievementProgressRepository.save(progress);
        if (managerAchievement.getPoints() == progress.getCurrentPoints()) {
            achievementService.giveAchievement(userId, managerAchievement);
        }
    }
}