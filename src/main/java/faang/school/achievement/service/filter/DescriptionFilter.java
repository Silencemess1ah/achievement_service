package faang.school.achievement.service.filter;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class DescriptionFilter implements AchievementFilter {

    @Override
    public boolean isApplicable(AchievementFilterDto filter) {
        return filter.getDescriptionPattern() != null && !filter.getDescriptionPattern().isEmpty();
    }

    @Override
    public Stream<AchievementDto> apply(Stream<AchievementDto> achievementStream, AchievementFilterDto filter) {
        return achievementStream.filter(achievement -> achievement.getDescription().toLowerCase()
                .contains(filter.getDescriptionPattern().toLowerCase()));
    }
}
