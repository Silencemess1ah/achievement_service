package faang.school.achievement.service.achievement_progress;

import faang.school.achievement.dto.achievement.AchievementDto;
import faang.school.achievement.dto.achievement.AchievementProgressDto;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.repository.AchievementProgressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementProgressServiceImplTest {

    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @Mock
    private AchievementProgressMapper achievementProgressMapper;

    @InjectMocks
    private AchievementProgressServiceImpl achievementProgressService;

    private final long userId = 1L;
    private AchievementProgress achievementProgress;
    private AchievementProgressDto achievementProgressDto;

    @BeforeEach
    void setUp() {
        Achievement achievement = Achievement.builder().id(2L).build();
        AchievementDto achievementDto = AchievementDto.builder().id(2L).build();

        achievementProgress = AchievementProgress.builder()
                .id(1L)
                .achievement(achievement)
                .userId(userId)
                .currentPoints(10L)
                .build();

        achievementProgressDto = AchievementProgressDto.builder()
                .id(1L)
                .achievement(achievementDto)
                .userId(userId)
                .currentPoints(10L)
                .build();
    }

    @Test
    void getAchievementProgressesByUserId() {
        when(achievementProgressRepository.findByUserId(userId)).thenReturn(List.of(achievementProgress));
        when(achievementProgressMapper.toDto(achievementProgress)).thenReturn(achievementProgressDto);

        List<AchievementProgressDto> actual = achievementProgressService.getAchievementProgressesByUserId(userId);
        assertIterableEquals(List.of(achievementProgressDto), actual);

        InOrder inOrder = inOrder(achievementProgressRepository, achievementProgressMapper);
        inOrder.verify(achievementProgressRepository).findByUserId(userId);
        inOrder.verify(achievementProgressMapper).toDto(achievementProgress);
    }
}