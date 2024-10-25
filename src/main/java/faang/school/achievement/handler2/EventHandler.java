package faang.school.achievement.handler2;

import faang.school.achievement.dto.EventBase;
import faang.school.achievement.dto.PostEvent;

public abstract class EventHandler<T extends EventBase> {
    public abstract void handle(T event);
}
