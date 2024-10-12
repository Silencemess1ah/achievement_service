package faang.school.achievement.handler;

import faang.school.achievement.dto.PostEvent;

public interface EventHandler {
    void handle(PostEvent event);
}
