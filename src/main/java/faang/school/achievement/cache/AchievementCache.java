package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AchievementCache {
    private final AchievementRepository achievementRepository;
    private Map<String, Achievement> ACHIEVEMENT_BY_TITLE;

    @PostConstruct
    public void createAchievementCache() {
        List<Achievement> achievements = achievementRepository.findAll();
        ACHIEVEMENT_BY_TITLE = achievements.stream()
                .collect(Collectors.toMap(achievement -> achievement.getTitle(),
                        achievement -> achievement));
        log.info("The cache was loaded successfully. He has {} achievements", ACHIEVEMENT_BY_TITLE.size());
    }

    public Achievement get(String title) {
        return ACHIEVEMENT_BY_TITLE.get(title);
    }
}
