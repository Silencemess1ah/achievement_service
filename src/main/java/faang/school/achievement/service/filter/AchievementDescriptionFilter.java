package faang.school.achievement.service.filter;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;
import org.springframework.stereotype.Component;

@Component
public class AchievementDescriptionFilter implements AchievementFilter {
    @Override
    public boolean isApplicable(AchievementFilterDto filter) {
        return filter.getDescriptionPattern() != null && !filter.getDescriptionPattern().isBlank();
    }

    @Override
    public boolean apply(Achievement achievement, AchievementFilterDto filter) {
        return achievement.getDescription().toLowerCase()
                .contains(filter.getDescriptionPattern().toLowerCase());
    }
}
