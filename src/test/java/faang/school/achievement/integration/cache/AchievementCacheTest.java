package faang.school.achievement.integration.cache;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.integration.IntegrationTestBase;
import faang.school.achievement.model.Achievement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AchievementCacheTest extends IntegrationTestBase {

    public static final String COLLECTOR = "COLLECTOR";

    @Autowired
    private AchievementCache achievementCache;


    @Test
    public void testAchievementCache() {

        Achievement result = achievementCache.get(COLLECTOR);

        assertEquals(COLLECTOR, result.getTitle());
        assertEquals(8, achievementCache.cacheSize());
    }
}