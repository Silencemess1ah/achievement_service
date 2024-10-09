package faang.school.achievement.event.handler;

public interface EventHandlerManager<T> {

    void processEvent(T event);
}
