package faang.school.achievement.service.eventhandler;

import faang.school.achievement.model.event.Event;

public interface EventHandler<T extends Event> {

    void handle(T event);
}
