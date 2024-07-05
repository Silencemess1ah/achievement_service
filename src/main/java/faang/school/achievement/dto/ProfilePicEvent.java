package faang.school.achievement.dto;

import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProfilePicEvent extends MessageEvent {
    private long userId;
    private LocalDateTime loadedAt;
}
