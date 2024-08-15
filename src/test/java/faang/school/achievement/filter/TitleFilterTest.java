package faang.school.achievement.filter;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.util.filter.TitleFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class TitleFilterTest {

    private final TitleFilter titleFilter = new TitleFilter();
    private final AchievementFilterDto filterDto = new AchievementFilterDto();

    public List<Achievement> prepareAchievements() {
        Achievement firstAchievement = Achievement.builder().
                title("Brilliant")
                .build();
        Achievement secondAchievement = Achievement.builder().
                title("Shiny")
                .build();
        return List.of(firstAchievement, secondAchievement);
    }

    @Test
    public void testTitleFilterIsApplicable() {
        filterDto.setTitle("Brilliant");

        boolean isApplicable = titleFilter.isApplicable(filterDto);

        assertTrue(isApplicable);
    }

    @Test
    public void testTitleFilterDoesNotIsApplicable() {

        boolean isApplicable = titleFilter.isApplicable(filterDto);

        assertFalse(isApplicable);
    }

    @Test
    public void testTitleFilterApplied() {
        filterDto.setTitle("Brilliant");
        List<Achievement> achievements = prepareAchievements();

        Achievement testFirstAchievement = Achievement.builder().
                title("Brilliant")
                .build();
        List<Achievement> testResultAchievements = List.of(testFirstAchievement);

        Stream<Achievement> filterResult = titleFilter.apply(achievements.stream(), filterDto);
        assertEquals(filterResult.toList(), testResultAchievements);
    }
}
