package faang.school.achievement.dto.event;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostEventDto {
    private Long id;
    @NotNull(message = "Receiver id can't be empty")
    private Long authorId;
    @NotNull(message = "Receiver id can't be empty")
    private Long userId;
    private LocalDateTime receivedAt;
}
