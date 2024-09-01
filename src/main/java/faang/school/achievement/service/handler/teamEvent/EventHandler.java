package faang.school.achievement.service.handler.teamEvent;

public interface EventHandler<T> {
    void process(T event);
}