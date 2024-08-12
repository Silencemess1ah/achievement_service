package faang.school.achievement;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {AchievementCache.class, Tests.class })
class AchievementCacheTest {

    @Autowired
    private AchievementCache achievementCache;


    @Test
    void testCacheAchievements() {
        Achievement result = achievementCache.getAchievement("Some");
        assertNotNull(result);
    }
}
