package faang.school.achievement.dto.handler;

public interface EventHandlerManager<T> {

    void processEvent(T event);
}
