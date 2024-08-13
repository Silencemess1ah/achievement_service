package faang.school.achievement.cache;

import faang.school.achievement.Tests;
import faang.school.achievement.model.Achievement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {AchievementCache.class, Tests.class })
@ActiveProfiles("default")
public class AchievementCacheTestWithData {

    @Autowired
    private AchievementCache achievementCache;

    @Test
    void testGetAchievementWhenDataExists() {
        Achievement result = achievementCache.getAchievement("Some");
        assertNotNull(result);
    }
}
