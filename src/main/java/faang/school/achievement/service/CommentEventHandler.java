package faang.school.achievement.service;

import faang.school.achievement.dto.CommentEvent;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentEventHandler implements EventHandler<CommentEvent> {

    private final AchievementService achievementService;
    private final AchievementRepository achievementRepository;

    private final static String ACHIEVEMENT_TITLE = "EXPERT";
    private final static Long ACHIEVEMENT_PROGRESS_POINTS = 1000L;

    @Override
    public Class<CommentEvent> getEventClass() {
        return CommentEvent.class;
    }

    @Override
    public void handleEvent(CommentEvent event) {
        Achievement achievement = achievementRepository.findByTitle(ACHIEVEMENT_TITLE).orElseThrow(
                () -> new EntityNotFoundException("Achievement %s progress not found".formatted(ACHIEVEMENT_TITLE)));

        if (!achievementService.hasAchievement(event.getIdAuthor(), achievement.getId())) {
            achievementService.createProgressIfNecessary(event.getIdAuthor(), achievement.getId());
        }

        AchievementProgress progress = achievementService.getProgress(event.getIdAuthor(), achievement.getId());

        if (progress.getCurrentPoints() < ACHIEVEMENT_PROGRESS_POINTS) {
            progress.increment();
        } else if (progress.getCurrentPoints() == ACHIEVEMENT_PROGRESS_POINTS) {
            achievementService.giveAchievement(event.getIdAuthor(), achievement);
        }
    }
}
