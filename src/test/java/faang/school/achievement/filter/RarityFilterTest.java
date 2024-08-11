package faang.school.achievement.filter;

import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.util.filter.RarityFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class RarityFilterTest {

    private final RarityFilter rarityFilter = new RarityFilter();
    private final AchievementFilterDto filterDto = new AchievementFilterDto();

    public List<Achievement> prepareAchievements() {
        Achievement firstAchievement = Achievement.builder().
                rarity(Rarity.RARE)
                .build();
        Achievement secondAchievement = Achievement.builder().
                rarity(Rarity.EPIC)
                .build();
        Achievement thirdAchievement = Achievement.builder().
                rarity(Rarity.RARE)
                .build();
        return List.of(firstAchievement, secondAchievement, thirdAchievement);
    }

    @Test
    public void testRarityFilterIsApplicable() {
        filterDto.setRarity(Rarity.COMMON);

        boolean isApplicable = rarityFilter.isApplicable(filterDto);

        assertTrue(isApplicable);
    }

    @Test
    public void testRarityFilterDoesNotIsApplicable() {

        boolean isApplicable = rarityFilter.isApplicable(filterDto);

        assertFalse(isApplicable);
    }

    @Test
    public void testRarityFilterApplied() {
        filterDto.setRarity(Rarity.RARE);
        List<Achievement> achievements = prepareAchievements();

        Achievement testFirstAchievement = Achievement.builder().
                rarity(Rarity.RARE)
                .build();
        List<Achievement> testResultAchievements = List.of(testFirstAchievement, testFirstAchievement);

        Stream<Achievement> filterResult = rarityFilter.apply(achievements.stream(), filterDto);
        assertEquals(filterResult.toList(), testResultAchievements);
    }
}
