package faang.school.achievement.filter;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.util.filter.DescriptionFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class DescriptionFilterTest {

    private final DescriptionFilter descriptionFilter = new DescriptionFilter();
    private final AchievementFilterDto filterDto = new AchievementFilterDto();

    public List<Achievement> prepareAchievements() {
        Achievement firstAchievement = Achievement.builder().
                description("For 100 comments")
                .build();
        Achievement secondAchievement = Achievement.builder().
                description("For 100 likes")
                .build();
        Achievement thirdAchievement = Achievement.builder().
                description("For 1000 comments")
                .build();
        return List.of(firstAchievement, secondAchievement, thirdAchievement);
    }

    @Test
    public void testDescriptionFilterIsApplicable() {
        filterDto.setDescriptionPattern("comments");

        boolean isApplicable = descriptionFilter.isApplicable(filterDto);

        assertTrue(isApplicable);
    }

    @Test
    public void testDescriptionFilterDoesNotIsApplicable() {

        boolean isApplicable = descriptionFilter.isApplicable(filterDto);

        assertFalse(isApplicable);
    }

    @Test
    public void testDescriptionFilterApplied() {
        filterDto.setDescriptionPattern("comments");
        List<Achievement> achievements = prepareAchievements();

        Achievement testFirstAchievement = Achievement.builder().
                description("For 100 comments")
                .build();
        Achievement testSecondAchievement = Achievement.builder().
                description("For 1000 comments")
                .build();
        List<Achievement> testResultAchievements = List.of(testFirstAchievement, testSecondAchievement);

        Stream<Achievement> filterResult = descriptionFilter.apply(achievements.stream(), filterDto);
        assertEquals(filterResult.toList(), testResultAchievements);
    }
}
