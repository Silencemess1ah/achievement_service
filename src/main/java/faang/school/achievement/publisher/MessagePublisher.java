package faang.school.achievement.publisher;

import faang.school.achievement.event.Event;

public interface MessagePublisher<T extends Event> {
    void publish(T event);
}
