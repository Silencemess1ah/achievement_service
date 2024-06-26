package faang.school.achievement.redis.handler;

public interface EventHandler<E> {
    void handleEvent(E event);
}
