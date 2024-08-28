package faang.school.achievement.filter.achievement;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class AchievementTitleFilter implements AchievementFilter {
    @Override
    public boolean isApplicable(AchievementFilterDto achievementFilterDto) {
        return achievementFilterDto.getTitlePattern() != null;
    }

    @Override
    public Stream<Achievement> filter(Stream<Achievement> achievementStream,
                                      AchievementFilterDto achievementFilterDto) {
        if (isApplicable(achievementFilterDto)) {
            return achievementStream.filter(achievement -> achievement.getTitle().toLowerCase()
                    .contains(achievementFilterDto.getTitlePattern().toLowerCase()));
        }
        return achievementStream;
    }
}
