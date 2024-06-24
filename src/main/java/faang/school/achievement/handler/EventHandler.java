package faang.school.achievement.handler;

import faang.school.achievement.event.Event;

public interface EventHandler<T extends Event> {
    void handle(T event);
}
