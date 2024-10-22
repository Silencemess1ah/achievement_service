package faang.school.achievement.handler;

public interface EventHandlerManager<T> {

    void processEvent(T event);
}
