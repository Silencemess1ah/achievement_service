package faang.school.achievement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AchievementEvent {

    private long id;

    private String title;

    private String description;

    private long userId;
}
