package faang.school.achievement.handler;

import faang.school.achievement.event.post.PostEvent;

public interface EventHandler<T> {
    void handle(T postEvent);
}
