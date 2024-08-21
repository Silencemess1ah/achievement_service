package faang.school.achievement.filter;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class AchievementDescriptionFilter implements AchievementFilter {
    @Override
    public boolean isApplicable(AchievementFilterDto achievementFilterDto) {
        return achievementFilterDto.getDescriptionPattern() != null;
    }

    @Override
    public Stream<Achievement> filter(Stream<Achievement> achievementStream,
                                      AchievementFilterDto achievementFilterDto) {
        if (isApplicable(achievementFilterDto)) {
            return achievementStream.filter(achievement -> achievement.getDescription().toLowerCase()
                    .contains(achievementFilterDto.getDescriptionPattern().toLowerCase()));
        }
        return achievementStream;
    }
}
