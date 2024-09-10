package faang.school.achievement.handler;

public interface EventHandler<T> {
    void handle(long userId);
}
