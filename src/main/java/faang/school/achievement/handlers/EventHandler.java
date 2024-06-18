package faang.school.achievement.handlers;

public interface EventHandler<T> {
    void handleEvent(T event);
}
