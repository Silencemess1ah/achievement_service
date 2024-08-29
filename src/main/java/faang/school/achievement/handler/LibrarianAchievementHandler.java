package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.event.AlbumCreatedEvent;
import faang.school.achievement.service.AchievementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LibrarianAchievementHandler extends AbstractAchievementHandler implements EventHandler<AlbumCreatedEvent> {

    @Value("${spring.achievement-handler.librarian-achievement-handler.achievement-name}")
    private String achievementTitle;

    public LibrarianAchievementHandler(AchievementService achievementService, AchievementCache achievementCache) {
        super(achievementService, achievementCache);
    }

    @Override
    public void handle(AlbumCreatedEvent event) {
        processAchievementEvent(achievementTitle, event.getUserId());
    }
}
