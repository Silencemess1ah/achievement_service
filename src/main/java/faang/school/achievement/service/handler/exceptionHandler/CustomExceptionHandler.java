package faang.school.achievement.service.handler.exceptionHandler;

import faang.school.achievement.exception.CustomJsonProcessingException;
import faang.school.achievement.exception.CustomReadValueException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Locale;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleEntityNotFoundException(EntityNotFoundException e, HttpServletRequest request) {
        String error = messageSource.getMessage("error.entity_not_found", null, Locale.getDefault());
        log.error(error, e);
        return buildErrorResponse(request.getRequestURI(), HttpStatus.CONFLICT.value(), error, e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(CustomReadValueException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleCustomReadValueException(CustomReadValueException e, HttpServletRequest request) {
        String error = messageSource.getMessage("error.read_value_exception", null, Locale.getDefault());;
        log.error(error, e);
        return buildErrorResponse(request.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR.value(), error, e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(CustomJsonProcessingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleCustomJsonProcessingException(CustomJsonProcessingException e, HttpServletRequest request) {

        String error = messageSource.getMessage("error.json_processing_exception", null, Locale.getDefault());
        log.error(error, e);
        return buildErrorResponse(request.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR.value(), error, e.getMessage(), LocalDateTime.now());
    }

    private ErrorResponse buildErrorResponse(String url, int status, String error, String message, LocalDateTime timestamp) {
        return ErrorResponse.builder()
                .url(url)
                .status(status)
                .error(error)
                .message(message)
                .timestamp(timestamp)
                .build();
    }
}