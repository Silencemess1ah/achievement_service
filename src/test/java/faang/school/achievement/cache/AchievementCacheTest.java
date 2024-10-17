package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import faang.school.achievement.profile.AchievementCacheProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {AchievementCache.class , AchievementCacheProfiles.class})
public class AchievementCacheTest {

    @Autowired
    private AchievementCache achievementCache;

    @Test
    @Profile("default")
    public void testGetByTitle() {
        Achievement result = achievementCache.getByTitle("Title");
        assertNotNull(result);
    }

    @Test
    @Profile("empty")
    public void testGetByNonExistsTitle() {
        assertThrows(EntityNotFoundException.class, () -> achievementCache.getByTitle("Non existent title"));
    }

}
