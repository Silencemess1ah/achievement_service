package faang.school.achievement.dto;

public enum SortField {
    TITLE("title"),
    RARITY("rarity"),
    CREATED_AT("createdAt");

    private final String value;

    SortField(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
