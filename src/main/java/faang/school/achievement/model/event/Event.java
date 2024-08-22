package faang.school.achievement.model.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Event { // все типы входящих событий можно привести к этому типу
    long userId;

    // остальные поля не нужны, игнорируем
}
