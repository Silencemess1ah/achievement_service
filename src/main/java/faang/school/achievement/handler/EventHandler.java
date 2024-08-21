package faang.school.achievement.handler;

import faang.school.achievement.dto.CommentEvent;

public interface EventHandler {
    void checkAndGetAchievement(CommentEvent commentEvent);
}
