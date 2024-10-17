package faang.school.achievement.service;

import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.dto.AchievementDto;
import faang.school.achievement.model.entity.Achievement;
import faang.school.achievement.model.entity.UserAchievement;
import faang.school.achievement.model.event.AchievementEvent;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.service.messaging.achievement.AchievementEventPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {

    @Mock
    private UserAchievementRepository userAchievementRepository;

    @Mock
    private AchievementRepository achievementRepository;

    @Mock
    private AchievementEventPublisher achievementEventPublisher;

    @Mock
    private AchievementMapper achievementMapper;

    @InjectMocks
    private AchievementService achievementService;

    @Test
    void assignAchievement_userAlreadyHasAchievement_shouldThrowException() {
        long userId = 1L;
        long achievementId = 100L;

        when(userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId)).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                achievementService.assignAchievement(userId, achievementId));

        assertEquals("User already has this achievement", exception.getMessage());
    }

    @Test
    void assignAchievement_achievementDoesNotExist_shouldThrowException() {
        long userId = 1L;
        long achievementId = 100L;

        when(userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId)).thenReturn(false);
        when(achievementRepository.existsById(achievementId)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                achievementService.assignAchievement(userId, achievementId));

        assertEquals("Achievement with this id 100 does not exist", exception.getMessage());
    }

    @Test
    void assignAchievement_successfulAssignment_shouldSaveUserAchievementAndPublishEvent() {
        long userId = 1L;
        long achievementId = 100L;
        Achievement achievement = new Achievement();

        when(userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId)).thenReturn(false);
        when(achievementRepository.existsById(achievementId)).thenReturn(true);
        when(achievementRepository.getReferenceById(achievementId)).thenReturn(achievement);

        achievementService.assignAchievement(userId, achievementId);

        ArgumentCaptor<UserAchievement> userAchievementCaptor = ArgumentCaptor.forClass(UserAchievement.class);
        verify(userAchievementRepository).save(userAchievementCaptor.capture());

        UserAchievement savedUserAchievement = userAchievementCaptor.getValue();
        assertEquals(userId, savedUserAchievement.getUserId());
        assertEquals(achievement, savedUserAchievement.getAchievement());

        ArgumentCaptor<AchievementEvent> eventCaptor = ArgumentCaptor.forClass(AchievementEvent.class);
        verify(achievementEventPublisher).publish(eventCaptor.capture());

        AchievementEvent publishedEvent = eventCaptor.getValue();
        assertEquals(userId, publishedEvent.userId());
        assertEquals(achievementId, publishedEvent.achievementId());
    }

    @Test
    void getAchievement_achievementDoesNotExist_shouldThrowException() {
        long achievementId = 100L;

        when(achievementRepository.existsById(achievementId)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                achievementService.getAchievement(achievementId));

        assertEquals("Achievement with this id 100 does not exist", exception.getMessage());
    }

    @Test
    void getAchievement_successfulRetrieval_shouldReturnAchievementDto() {
        long achievementId = 100L;
        Achievement achievement = new Achievement();
        AchievementDto achievementDto = new AchievementDto();

        when(achievementRepository.existsById(achievementId)).thenReturn(true);
        when(achievementRepository.getReferenceById(achievementId)).thenReturn(achievement);
        when(achievementMapper.entityToDto(achievement)).thenReturn(achievementDto);

        AchievementDto result = achievementService.getAchievement(achievementId);

        assertEquals(achievementDto, result);
        verify(achievementMapper).entityToDto(achievement);
    }
}