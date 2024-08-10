package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@Slf4j
@RequiredArgsConstructor
public class AchievementCache {
    private final AchievementRepository achievementRepository;
    private Map<String, Achievement> achievementsCacheMap;

    @PostConstruct
    private void initCache() {
        achievementsCacheMap = StreamSupport.stream(achievementRepository.findAll().spliterator(), false)
                .collect(Collectors.toMap(
                        Achievement::getTitle,
                        achievement -> achievement
                ));
        log.info("Achievement cache has been initialized/updated. Cache size = {}" , achievementsCacheMap.size());
    }

    public Achievement getAchievementByTitle(String title) {
        return achievementsCacheMap.get(title);
    }

    @Scheduled(cron = "${cache.achievement.update-schedule}")
    public void updateCache() {
        log.info("Starting update of  Achievement cache");
        this.initCache();
    }
}
