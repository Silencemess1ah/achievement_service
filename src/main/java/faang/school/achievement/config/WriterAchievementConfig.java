package faang.school.achievement.config;

import faang.school.achievement.handler.WriterAchievementHandler;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.UserAchievementService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WriterAchievementConfig {

    private final AchievementService achievementService;
    private final UserAchievementService userAchievementService;

    @Value("${achievement.writer.points}")
    private int writerPoints;

    @Bean
    public WriterAchievementHandler writerAchievementHandler() {
        return new WriterAchievementHandler(achievementService, userAchievementService, writerPoints);
    }
}
