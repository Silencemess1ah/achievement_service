package faang.school.achievement.profile;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@Configuration
public class AchievementCacheProfiles {

    @Bean
    @Profile("default")
    public AchievementRepository achievementRepository() {
        Achievement achievement = Achievement.builder()
                .title("Title")
                .build();
        List<Achievement> achievements = List.of(achievement);
        AchievementRepository achievementRepository = mock(AchievementRepository.class);
        when(achievementRepository.findAll()).thenReturn(achievements);
        return achievementRepository;
    }

    @Bean
    @Profile("empty")
    public AchievementRepository emptyAchievementRepository() {
        AchievementRepository achievementRepository = mock(AchievementRepository.class);
        when(achievementRepository.findAll()).thenReturn(List.of());
        return achievementRepository;
    }
}
