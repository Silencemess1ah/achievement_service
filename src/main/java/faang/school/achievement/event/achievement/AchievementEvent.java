package faang.school.achievement.event.achievement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import faang.school.achievement.event.Event;
import lombok.*;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.repository.AchievementRepository;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
@Jacksonized
public class AchievementEvent implements Event {
    /**
     * ID of the user who received the achievement
     */
    private final Long userId;
    /**
     * ID of record in {@link UserAchievementRepository}. ID achievement for a specific user.
     */
    private final Long userAchievementId;
    /**
     * ID achievement in {@link AchievementRepository}
     */
    private final Long achievementId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private final LocalDateTime receiveAt;
}
