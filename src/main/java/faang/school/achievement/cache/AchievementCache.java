package faang.school.achievement.cache;

import faang.school.achievement.model.Achievement;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@Slf4j
@RequiredArgsConstructor
public class AchievementCache {

    private final Map<String, Achievement> cache = new HashMap<>();

    public Achievement getAchievement(@NotBlank(message = "Title can't be neither blank nor null!")
                                      String achievementTitle) {
        if (cache.containsKey(achievementTitle)) {
            log.debug("Title {} is correct and exists in cache", achievementTitle);
            return cache.get(achievementTitle);
        } else {
            log.error("Such title {} does not exist!", achievementTitle);
            throw new RuntimeException();
        }
    }
}
