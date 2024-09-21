package faang.school.achievement.service.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface MessagePublisher<T> {
    void publish(T event) throws JsonProcessingException;
}
