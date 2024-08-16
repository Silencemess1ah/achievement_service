package faang.school.achievement.mapper;

import faang.school.achievement.dto.achievement.AchievementDto;
import faang.school.achievement.dto.achievement.AchievementProgressDto;
import faang.school.achievement.dto.achievement.UserAchievementDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.model.UserAchievement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


public class AchievementMapperTest {
    private final AchievementMapperImpl achievementMapper = new AchievementMapperImpl();
    private Achievement achievement;
    private UserAchievement userAchievement;
    private AchievementProgress achievementProgress;
    private AchievementDto achievementDto;
    private UserAchievementDto userAchievementDto;
    private AchievementProgressDto achievementProgressDto;

    @BeforeEach
    public void init() {
        long points = 3L;
        long id = 1L;
        long userId = 2L;
        long currentPoints = 4;
        LocalDateTime now = LocalDateTime.now();
        Rarity rarity = Rarity.COMMON;
        achievement = Achievement.builder()
                .id(id)
                .title("Title")
                .description("Desc")
                .rarity(rarity)
                .points(points)
                .build();
        userAchievement = UserAchievement.builder()
                .id(id)
                .achievement(achievement)
                .userId(userId)
                .createdAt(now)
                .updatedAt(now)
                .build();
        achievementProgress = AchievementProgress.builder()
                .id(id)
                .achievement(achievement)
                .userId(userId)
                .currentPoints(currentPoints)
                .createdAt(now)
                .updatedAt(now)
                .version(4L)
                .build();
        achievementDto = AchievementDto.builder()
                .id(id)
                .title("Title")
                .description("Desc")
                .rarity(rarity)
                .points(points)
                .build();
        userAchievementDto = UserAchievementDto.builder()
                .id(id)
                .achievementId(achievement.getId())
                .achievementTitle(achievement.getTitle())
                .achievementDescription(achievement.getDescription())
                .achievementRarity(achievement.getRarity())
                .userId(userId)
                .createdAt(now)
                .updatedAt(now)
                .build();
        achievementProgressDto = AchievementProgressDto.builder()
                .id(id)
                .achievementId(achievement.getId())
                .achievementTitle(achievement.getTitle())
                .achievementDescription(achievement.getDescription())
                .achievementRarity(achievement.getRarity())
                .currentPoints(currentPoints)
                .userId(userId)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    @Test
    public void testToDtoAchievement() {
        AchievementDto result = achievementMapper.toDto(achievement);

        Assertions.assertEquals(result, achievementDto);
    }

    @Test
    public void testToDtoUserAchievement() {
        UserAchievementDto result = achievementMapper.toDto(userAchievement);

        Assertions.assertEquals(result, userAchievementDto);
    }

    @Test
    public void testToDtoAchievementProgress() {
        AchievementProgressDto result = achievementMapper.toDto(achievementProgress);

        Assertions.assertEquals(result, achievementProgressDto);
    }

    @Test
    public void testToEntityAchievement() {
        Achievement result = achievementMapper.toEntity(achievementDto);

        Assertions.assertEquals(result, achievement);
    }

    @Test
    public void testToEntityUserAchievement() {
        UserAchievement result = achievementMapper.toEntity(userAchievementDto);
        userAchievement.setAchievement(null);
        Assertions.assertEquals(result, userAchievement);
    }

    @Test
    public void testToEntityAchievementProgress() {
        AchievementProgress result = achievementMapper.toEntity(achievementProgressDto);
        achievementProgress.setAchievement(null);
        achievementProgress.setVersion(0L);
        Assertions.assertEquals(result, achievementProgress);
    }
}
