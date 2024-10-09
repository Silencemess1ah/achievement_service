package faang.school.achievement.listener;

public interface EventListener<T> {
    void onMessage(T event);
}
