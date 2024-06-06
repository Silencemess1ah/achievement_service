package faang.school.achievement.event.handler;

import faang.school.achievement.dto.Event;

public interface EventHandler<T extends Event> {
    void handleEvent(T event);
}
