package faang.school.achievement.handler;

import faang.school.achievement.dto.CommentEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.service.AchievementService;
import org.springframework.stereotype.Service;

@Service
public class ExpertAchievementHandler extends CommentEventHandler {

    public ExpertAchievementHandler(AchievementService achievementService) {
        super(achievementService);
    }

    @Override
    public void handle(CommentEventDto event) {
        String achievementTitle = "EXPERT";
        Achievement achievement = achievementService.getAchievementFromCache(achievementTitle);
        handleProgress(event, achievement);
    }
}