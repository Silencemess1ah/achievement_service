package faang.school.achievement.service.handlers;

public interface EventHandler<T> {

    void handle(T event);
}
