package faang.school.achievement.publisher;

import org.springframework.http.ResponseEntity;

public interface MessagePublisher<T> {
    ResponseEntity<String> publish(T message);
}
