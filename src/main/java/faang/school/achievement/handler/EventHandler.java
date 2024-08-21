package faang.school.achievement.handler;

public interface EventHandler<T> {
    public void handle(T event);
}
