package faang.school.achievement.handler;

import faang.school.achievement.exception.DataValidationException;
import faang.school.achievement.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String LOG_MESSAGE_EXCEPTION = "In class: {}, message: {}, error: {}, time: {}";

    @ExceptionHandler(DataValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDto handleDataValidationException(DataValidationException e) {
        loggingInfo(e);
        return constructorForErrorMessageDto(e);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDto handleEntityNotFoundException(EntityNotFoundException e) {
        loggingInfo(e);
        return constructorForErrorMessageDto(e);
    }

    private void loggingInfo(Exception e) {
        log.info(LOG_MESSAGE_EXCEPTION, e.getClass(), e.getMessage(), e.getCause(), LocalDateTime.now());
    }

    private ErrorMessageDto constructorForErrorMessageDto(Exception e) {
        return ErrorMessageDto.builder()
                .message(e.getMessage())
                .description(String.valueOf(e.getCause()))
                .timestamp(LocalDateTime.now())
                .build();
    }
}
