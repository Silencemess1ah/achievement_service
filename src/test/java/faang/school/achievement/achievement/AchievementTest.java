package faang.school.achievement.achievement;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.service.AchievementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.config.context.UserContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AchievementTest {

    @InjectMocks
    private AchievementService achievementService;
    @Mock
    private UserContext userContext;
    @Mock
    private AchievementMapper achievementMapper;
    @Mock
    private AchievementRepository achievementRepository;

    private AchievementDto achievementDto;
    private Achievement achievement;

    @BeforeEach
    void setUp() {
        achievementDto = new AchievementDto(1L, "title", Rarity.COMMON);
        achievement = new Achievement(1L, "title", "desc", Rarity.COMMON,
                List.of(), List.of(), 1L, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void createAchievement() {
        when(achievementMapper.toEntity(achievementDto)).thenReturn(achievement);
        when(achievementRepository.existsByTitle(achievement.getTitle())).thenReturn(false);

        achievementService.createAchievement(achievementDto);

        assertEquals(1L, achievementDto.getId());
    }

    @Test
    void createAchievementExs() {
        when(achievementMapper.toEntity(achievementDto)).thenReturn(achievement);
        when(achievementRepository.existsByTitle(achievement.getTitle())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                achievementService.createAchievement(achievementDto));

        assertEquals("exception", exception.getMessage());
    }
}