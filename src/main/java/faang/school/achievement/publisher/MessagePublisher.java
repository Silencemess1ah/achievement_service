package faang.school.achievement.publisher;

/**
 * @author Evgenii Malkov
 */
public interface MessagePublisher<T> {

    void publish(T message);

}
