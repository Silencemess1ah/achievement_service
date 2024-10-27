package faang.school.achievement.service.filter;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;
import org.springframework.stereotype.Component;

@Component
public class AchievementTitleFilter implements AchievementFilter {
    @Override
    public boolean isApplicable(AchievementFilterDto filter) {
        return filter.getTitlePattern() != null && !filter.getTitlePattern().isBlank();
    }

    @Override
    public boolean apply(Achievement achievement, AchievementFilterDto filter) {
        return achievement.getTitle().name().toLowerCase()
                .contains(filter.getTitlePattern().toLowerCase());
    }
}
