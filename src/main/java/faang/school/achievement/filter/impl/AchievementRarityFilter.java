package faang.school.achievement.filter.impl;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.filter.AchievementFilter;
import faang.school.achievement.model.Achievement;
import org.springframework.stereotype.Component;

@Component
public class AchievementRarityFilter implements AchievementFilter {
    @Override
    public boolean isApplicable(AchievementFilterDto filters) {
        return filters.getRarity() != null;
    }

    @Override
    public boolean test(Achievement entity, AchievementFilterDto filters) {
        return entity.getRarity().equals(filters.getRarity());
    }
}
