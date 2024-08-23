package faang.school.achievement.filter.achievement;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;

import java.util.stream.Stream;

public interface AchievementFilter {

    boolean isApplicable(AchievementFilterDto achievementFilterDto);

    Stream<Achievement> filter(Stream<Achievement> achievementStream,
                               AchievementFilterDto achievementFilterDto);
}

