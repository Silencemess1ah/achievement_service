package faang.school.achievement.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class AlbumCreatedEvent extends Event {
    private Long userId;
    private Long albumId;
    private String albumName;

    public AlbumCreatedEvent(LocalDateTime eventTime) {
        super(eventTime);
    }
}
