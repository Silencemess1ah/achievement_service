package faang.school.achievement.service.user_achievement;

import faang.school.achievement.dto.achievement.AchievementDto;
import faang.school.achievement.dto.achievement.UserAchievementDto;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.UserAchievementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAchievementServiceImplTest {

    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Mock
    private UserAchievementMapper userAchievementMapper;
    @Mock
    private AchievementMapper achievementMapper;

    @InjectMocks
    private UserAchievementServiceImpl userAchievementService;

    private final long userId = 1L;
    private final long achievementId = 2L;
    private UserAchievement userAchievement;
    private UserAchievementDto userAchievementDto;

    @BeforeEach
    void setUp() {
        Achievement achievement = Achievement.builder().id(achievementId).build();
        AchievementDto achievementDto = AchievementDto.builder().id(achievementId).build();

        userAchievement = UserAchievement.builder()
                .id(1L)
                .achievement(achievement)
                .userId(userId)
                .build();

        userAchievementDto = UserAchievementDto.builder()
                .id(1L)
                .achievement(achievementDto)
                .userId(userId)
                .build();
    }

    @Test
    void getAchievementsByUserId() {
        when(userAchievementRepository.findByUserId(userId)).thenReturn(List.of(userAchievement));
        when(userAchievementMapper.toDto(userAchievement)).thenReturn(userAchievementDto);

        List<UserAchievementDto> actual = userAchievementService.getAchievementsByUserId(userId);
        assertIterableEquals(List.of(userAchievementDto), actual);

        InOrder inOrder = inOrder(userAchievementRepository, userAchievementMapper);
        inOrder.verify(userAchievementRepository).findByUserId(userId);
        inOrder.verify(userAchievementMapper).toDto(userAchievement);
    }

    @Test
    void hasAchievement() {
        when(userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId)).thenReturn(true);

        boolean actual = userAchievementService.hasAchievement(userId, achievementId);
        assertTrue(actual);

        InOrder inOrder = inOrder(userAchievementRepository);
        inOrder.verify(userAchievementRepository).existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Test
    void giveAchievement() {
        AchievementDto achievementDto = AchievementDto.builder()
                .id(achievementId)
                .title("title")
                .description("description")
                .points(10L)
                .rarity(Rarity.RARE)
                .build();

        Achievement achievement = Achievement.builder()
                .id(achievementId)
                .title("title")
                .description("description")
                .points(10L)
                .rarity(Rarity.RARE)
                .build();

        when(achievementMapper.toEntity(achievementDto)).thenReturn(achievement);

        userAchievementService.giveAchievement(userId, achievementDto);

        InOrder inOrder = inOrder(userAchievementRepository);
        inOrder.verify(userAchievementRepository).save(any(UserAchievement.class));
    }
}