package faang.school.achievement.service;

import faang.school.achievement.dto.CommentEvent;

public interface EventHandler<T> {

    void handleEvent(T event);

    Class<T> getEventClass();
}
