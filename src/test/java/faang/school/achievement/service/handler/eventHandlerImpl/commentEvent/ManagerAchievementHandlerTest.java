package faang.school.achievement.service.handler.eventHandlerImpl.commentEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.dto.event.TeamEvent;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import faang.school.achievement.service.handler.eventHandler.teamEvent.ManagerAchievementHandler;
import faang.school.achievement.service.publisher.AchievementPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static faang.school.achievement.model.Rarity.COMMON;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManagerAchievementHandlerTest {
    private static final String TITLE_OF_ACHIEVEMENT = "MANAGER";
    private static final long VALID_ID = 1L;
    private static final long POINTS = 10L;
    private TeamEvent teamEvent;
    private Achievement achievement;
    private AchievementProgress achievementProgress;
    private AchievementProgressDto progressDto;
    @Mock
    private AchievementCache achievementCache;
    @Mock
    private AchievementService achievementService;
    @Mock
    private AchievementPublisher achievementPublisher;
    @Mock
    private AchievementMapper mapper;
    private ManagerAchievementHandler handler;

    @BeforeEach
    void setUp() {
        handler = new ManagerAchievementHandler(achievementCache, achievementService, achievementPublisher,
                TITLE_OF_ACHIEVEMENT, mapper);
        teamEvent = TeamEvent.builder()
                .projectId(VALID_ID)
                .userId(VALID_ID)
                .teamId(VALID_ID)
                .build();
        achievement = Achievement.builder()
                .id(VALID_ID)
                .title(TITLE_OF_ACHIEVEMENT)
                .points(POINTS)
                .rarity(COMMON)
                .build();
        AchievementDto achievementDto = AchievementDto.builder()
                .id(achievement.getId())
                .title(achievement.getTitle())
                .points(achievement.getPoints())
                .rarity(achievement.getRarity())
                .build();
        achievementProgress = AchievementProgress.builder()
                .id(VALID_ID)
                .achievement(achievement)
                .userId(VALID_ID)
                .currentPoints(0L)
                .build();
        progressDto = AchievementProgressDto.builder()
                .id(VALID_ID)
                .userId(VALID_ID)
                .achievement(achievementDto)
                .currentPoints(achievementProgress.getCurrentPoints())
                .build();
    }

    @Test
    void testValidGiveAchievement() throws JsonProcessingException {
        //Act
        achievementProgress.setCurrentPoints(POINTS - 1);
        when(achievementCache.get(achievement.getTitle())).thenReturn(achievement);
        when(achievementService.hasAchievement(teamEvent.getUserId() ,achievement.getId())).thenReturn(false);
        when(achievementService.getProgress(teamEvent.getUserId() ,achievement.getId())).thenReturn(progressDto);
        when(mapper.toAchievementProgress(any())).thenReturn(achievementProgress);
        handler.process(teamEvent);
        //Assert
        verify(achievementService).giveAchievement(teamEvent.getUserId(), achievement);
    }

    @Test
    void testWithoutGiveAchievement() throws JsonProcessingException {
        //Arrange
        achievement.setPoints(VALID_ID + 1);
        //Act
        when(achievementCache.get(any())).thenReturn(achievement);
        when(achievementService.hasAchievement(anyLong(), anyLong())).thenReturn(false);
        when(achievementService.getProgress(anyLong(), anyLong())).thenReturn(progressDto);
        when(mapper.toAchievementProgress(any())).thenReturn(achievementProgress);
        handler.process(teamEvent);
        //Assert
        verify(achievementService).saveProgress(any());
    }

    @Test
    void testUserHasAchievement() throws JsonProcessingException {
        //Arrange
        achievement.setPoints(VALID_ID);
        //Act
        when(achievementCache.get(any())).thenReturn(achievement);
        when(achievementService.hasAchievement(anyLong(), anyLong())).thenReturn(true);
        handler.process(teamEvent);
        //Assert
        verify(achievementService, never()).getProgress(anyLong(), any());
    }
}