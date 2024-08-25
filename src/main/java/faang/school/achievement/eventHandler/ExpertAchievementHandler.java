package faang.school.achievement.eventHandler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.CommentEvent;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExpertAchievementHandler extends CommentEventHandler{

    private final AchievementService achievementService;
    private final AchievementCache achievementCache;

    @Override
    public void handle(CommentEvent event) {

    }
}
