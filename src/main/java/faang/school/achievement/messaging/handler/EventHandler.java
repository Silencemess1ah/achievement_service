package faang.school.achievement.messaging.handler;

public interface EventHandler<T> {
    void handle(T postEvent);
}
