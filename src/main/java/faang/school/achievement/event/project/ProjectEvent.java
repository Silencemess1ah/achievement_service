package faang.school.achievement.event.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import faang.school.achievement.event.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectEvent implements Event {
    private UUID eventId;
    private long authorId;
    private long projectId;
    private LocalDateTime timeStamp;

    @Override
    public long getUserId() {
        return authorId;
    }
}
