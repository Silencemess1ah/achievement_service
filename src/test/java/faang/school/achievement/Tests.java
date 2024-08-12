package faang.school.achievement;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static org.mockito.Mockito.when;

@Configuration
public class Tests {

    @Bean
    public AchievementRepository achievementRepository() {
        Achievement expected = new Achievement();
        expected.setTitle("Some");
        List<Achievement> expectedAchievements = List.of(expected);
        // using Mockito is just an example
        AchievementRepository achievementRepository = Mockito.mock(AchievementRepository.class);
        when(achievementRepository.findAll()).thenReturn(expectedAchievements);
        return achievementRepository;
    }

}
