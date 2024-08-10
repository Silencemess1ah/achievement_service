package faang.school.achievement.publisher;

public interface Publisher<T> {
    void publish(T event);
}