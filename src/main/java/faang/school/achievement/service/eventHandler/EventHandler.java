package faang.school.achievement.service.eventHandler;

public interface EventHandler<T> {
    void handle(T event);
}
