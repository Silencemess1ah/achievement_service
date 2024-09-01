package faang.school.achievement.mapper;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.util.AchievementTestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AchievementMapperTest {
    private final AchievementMapper mapper = new AchievementMapperImpl();
    private AchievementTestContainer container;

    @BeforeEach
    void setUp() {
        container = new AchievementTestContainer();
    }

    @Test
    void testToAchievementDto() {
        // given
        Achievement entity = container.achievement();
        AchievementDto dtoExp = AchievementDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .points(entity.getPoints())
                .rarity(entity.getRarity())
                .build();

        // when
        AchievementDto dtoActual = mapper.toAchievementDto(entity);

        // then
        assertEquals(dtoExp, dtoActual);
    }

    @Test
    void testToAchievementProgressDto() {
        // given
        AchievementProgress entity = container.achievementProgress();
        AchievementProgressDto dtoExp = AchievementProgressDto.builder()
                .id(entity.getId())
                .achievement(mapper.toAchievementDto(entity.getAchievement()))
                .userId(entity.getUserId())
                .currentPoints(entity.getCurrentPoints())
                .version(entity.getVersion())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();

        // when
        AchievementProgressDto dtoActual = mapper.toAchievementProgressDto(entity);

        // then
        assertEquals(dtoExp, dtoActual);
    }

    @Test
    void testToAchievementProgress() {
        // given
        AchievementProgressDto dto = container.achievementProgresDto();
        dto.setAchievement(null);
        AchievementProgress achievementProgress = AchievementProgress.builder()
                .id(dto.getId())
                .achievement(null)
                .userId(dto.getUserId())
                .currentPoints(dto.getCurrentPoints())
                .build();

        // when
        AchievementProgress dtoActual = mapper.toAchievementProgress(dto);

        // then
        assertEquals(achievementProgress, dtoActual);
    }
}
