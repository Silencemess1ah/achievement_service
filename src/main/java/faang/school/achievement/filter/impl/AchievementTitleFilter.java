package faang.school.achievement.filter.impl;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.filter.AchievementFilter;
import faang.school.achievement.model.Achievement;
import org.springframework.stereotype.Component;

@Component
public class AchievementTitleFilter implements AchievementFilter {
    @Override
    public boolean isApplicable(AchievementFilterDto filters) {
        return filters.getTitle() != null;
    }

    @Override
    public boolean test(Achievement entity, AchievementFilterDto filters) {
        String titleEntity = entity.getTitle().toLowerCase();
        String titleFilter = filters.getTitle().toLowerCase();
        return titleEntity.contains(titleFilter);
    }
}
