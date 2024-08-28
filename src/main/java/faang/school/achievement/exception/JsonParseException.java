package faang.school.achievement.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonParseException extends RuntimeException {
    public JsonParseException(String message) {
        super(message);
        log.error(message, this);
    }
}
