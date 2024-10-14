package faang.school.achievement.service;

import faang.school.achievement.dto.CommentEvent;

public interface EventHandler {

    void handleEvent(CommentEvent event);

}
