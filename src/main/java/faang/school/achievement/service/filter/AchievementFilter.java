package faang.school.achievement.service.filter;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;

import java.util.stream.Stream;

public interface AchievementFilter {

    boolean isApplicable(AchievementFilterDto filter);

    Stream<AchievementDto> apply(Stream<AchievementDto> achievementStream, AchievementFilterDto filter);
}
