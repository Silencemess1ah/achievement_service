package faang.school.achievement.service.messaging;

public interface MessagePublisher<T> {
    void publish(T message);
}
