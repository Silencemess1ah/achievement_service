package faang.school.achievement.exception;

/**
 * @author Evgenii Malkov
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
