package faang.school.achievement.service.handler.commentEvent;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface EventHandler<T> {
    void process(T event) throws JsonProcessingException;
}
