package faang.school.achievement.messaging.handler.project;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.project.ProjectEvent;
import faang.school.achievement.messaging.handler.AbstractEventHandler;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.service.AchievementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BusinessmanAchievementHandler extends AbstractEventHandler<ProjectEvent>{

    public BusinessmanAchievementHandler(AchievementCache achievementCache,
                                         AchievementService achievementService,
                                         @Value("${listener.type.achievements.businessman}") String title,
                                         AchievementProgressRepository achievementProgressRepository) {
        super(achievementCache, achievementService, title, achievementProgressRepository);
    }

    @Override
    public void handle(ProjectEvent event) {
        processEvent(event);
    }
}
