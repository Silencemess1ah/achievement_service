package faang.school.achievement.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface MessagePublisher<T> {
    void publish(T event) throws JsonProcessingException;
}
