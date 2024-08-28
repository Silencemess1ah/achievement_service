package faang.school.achievement.handler;

public interface EventHandler<T> {
    void process(T event);
}