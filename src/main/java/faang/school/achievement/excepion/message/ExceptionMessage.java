package faang.school.achievement.excepion.message;

public enum ExceptionMessage {

    NO_ACHIEVEMENT_IN_DB("There is no achievement in database"),

    NO_ACHIEVEMENT_PROGRESS("No achievement progress");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
