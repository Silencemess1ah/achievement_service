package faang.school.achievement.service;

import faang.school.achievement.exception.NotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@Validated
@Getter
@RequiredArgsConstructor
@Slf4j
public class AchievementCache {
    private Map<String, Achievement> achievements;
    private final AchievementRepository achievementRepository;

    @PostConstruct
    @Scheduled(cron = "@daily")
    protected void load() {
        log.info("Loading achievement cache");
        achievements = achievementRepository.findAll().stream()
                .collect(Collectors.toMap(Achievement::getTitle, achievement -> achievement));
    }

    public Achievement get(@NotBlank String title) {
        Achievement achievement = achievements.get(title);
        if (achievement == null) {
            throw new NotFoundException("Achievement '" + title + "' not found");
        }
        return achievement;
    }
}
