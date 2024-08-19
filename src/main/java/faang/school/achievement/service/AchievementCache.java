package faang.school.achievement.service;

import faang.school.achievement.dto.achievement.AchievementDto;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.validator.AchievementValidator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AchievementCache {

    private final Map<String, AchievementDto> achievements;
    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;
    private final AchievementValidator achievementValidator;

    @PostConstruct
    public void init() {
        List<AchievementDto> allAchievements = achievementMapper.toDtoList(achievementRepository.findAll());
        Map<String, AchievementDto> map =  allAchievements.stream()
                .collect(Collectors.toMap(AchievementDto::getTitle, achievementDto -> achievementDto));
        achievements.putAll(map);
    }

    public AchievementDto get(String achievementTitle) {
        achievementValidator.checkTitle(achievementTitle);

        return achievements.get(achievementTitle);
    }
}
