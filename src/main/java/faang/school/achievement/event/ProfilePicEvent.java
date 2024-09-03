package faang.school.achievement.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfilePicEvent {
    private long userId;
    private String pictureUrl;
}
