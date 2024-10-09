package faang.school.achievement.event.handler;

public interface EventHandler<T> {

    void handleEvent(T event);

    Class<T> getEventClass();
}
