package faang.school.achievement.filter.impl;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.filter.AchievementFilter;
import faang.school.achievement.model.Achievement;
import org.springframework.stereotype.Component;

@Component
public class AchievementDescriptionFilter implements AchievementFilter {
    @Override
    public boolean isApplicable(AchievementFilterDto filters) {
        return filters.getDescription() != null;
    }

    @Override
    public boolean test(Achievement entity, AchievementFilterDto filters) {
        String descriptionEntity = entity.getDescription().toLowerCase();
        String descriptionFilter = filters.getDescription().toLowerCase();
        return descriptionEntity.contains(descriptionFilter);
    }
}
