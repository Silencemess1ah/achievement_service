package faang.school.achievement.publisher;

import faang.school.achievement.event.Event;

public interface Publisher<E extends Event> {
    void publish(E event);
}
