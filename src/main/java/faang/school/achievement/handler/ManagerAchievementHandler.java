package faang.school.achievement.handler;

import faang.school.achievement.dto.TeamEvent;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@Async
public class ManagerAchievementHandler extends TeamEventHandler {
    @Value("${data.achievements.titles.manager}")
    private String achievementsTitle;

    public ManagerAchievementHandler(
            AchievementCache achievementCache,
            AchievementService achievementService,
            AchievementProgressRepository achievementProgressRepository,
            AchievementMapper mapper) {
        super(achievementCache, achievementService, achievementProgressRepository, mapper);
    }

    public void process(TeamEvent teamEvent) {
        processTeamEvent(teamEvent, achievementsTitle);
    }
}