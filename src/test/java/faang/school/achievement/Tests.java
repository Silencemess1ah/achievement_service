package faang.school.achievement;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@Configuration
public class Tests {

    @Bean
    @Profile("default")
    public AchievementRepository achievementRepository() {
        Achievement expected = new Achievement();
        expected.setTitle("Some");
        List<Achievement> expectedAchievements = List.of(expected);
        // using Mockito is just an example
        AchievementRepository achievementRepository = Mockito.mock(AchievementRepository.class);
        when(achievementRepository.findAll()).thenReturn(expectedAchievements);
        return achievementRepository;
    }

    @Bean
    @Profile("empty")
    public AchievementRepository achievementRepositoryWithoutData() {
        AchievementRepository achievementRepository = Mockito.mock(AchievementRepository.class);
        when(achievementRepository.findAll()).thenReturn(Collections.emptyList());
        return achievementRepository;
    }

}
