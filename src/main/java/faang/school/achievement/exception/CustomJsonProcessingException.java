package faang.school.achievement.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

public class CustomJsonProcessingException extends RuntimeException {
    public CustomJsonProcessingException(JsonProcessingException e) {
    }
}
