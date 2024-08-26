package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.exception.NotFoundException;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.mapper.AchievementProgressMapper;
import faang.school.achievement.mapper.UserAchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementEvent;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.redis.AchievementPublisher;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.util.filter.AchievementFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AchievementServiceTest {

    AchievementRepository achievementRepository = Mockito.mock(AchievementRepository.class);
    UserAchievementRepository userAchievementRepository = Mockito.mock(UserAchievementRepository.class);
    AchievementProgressRepository achievementProgressRepository = Mockito.mock(AchievementProgressRepository.class);
    AchievementMapper achievementMapperMock = Mockito.mock(AchievementMapper.class);
    UserAchievementMapper userAchievementMapper = Mockito.mock(UserAchievementMapper.class);
    AchievementProgressMapper achievementProgressMapper = Mockito.mock(AchievementProgressMapper.class);
    AchievementFilter filterMock = Mockito.mock(AchievementFilter.class);
    List<AchievementFilter> filters = List.of(filterMock);

    AchievementPublisher achievementPublisher = Mockito.mock(AchievementPublisher.class);

    AchievementService achievementService = new AchievementService(
            achievementRepository,
            userAchievementRepository,
            achievementProgressRepository,
            achievementMapperMock,
            userAchievementMapper,
            achievementProgressMapper,
            filters,
            achievementPublisher);

    Achievement achievement = new Achievement();
    AchievementDto achievementDto = new AchievementDto();
    private final AchievementFilterDto filterDto = new AchievementFilterDto();

    public AchievementDto prepareAchievementDto() {
        achievementDto = AchievementDto.builder()
                .id(1L)
                .build();
        return achievementDto;
    }

    @Test
    public void testGetAllAchievementSuccessful() {

        achievementService.getAllAchievement(filterDto);

        verify(achievementRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllAchievementApplyTitleFilter() {
        Stream<Achievement> achievementStream = Stream.of(new Achievement());
        AchievementDto dto = prepareAchievementDto();
        when(filters.get(0).isApplicable(new AchievementFilterDto())).thenReturn(true);
        when(filters.get(0).apply(any(), any())).thenReturn(achievementStream);
        when(achievementMapperMock.toDtoList(List.of(achievement))).thenReturn(List.of(dto));

        List<AchievementDto> methodResult = achievementService.getAllAchievement(filterDto);

        assertEquals(methodResult, List.of(dto));
    }

    @Test
    public void testGetAchievementsByUserIdIfNoAchievementsFound() {
        long userId = 1L;
        when(userAchievementRepository.findByUserId(anyLong())).thenReturn(null);

        assertThrows(NotFoundException.class, () -> achievementService.getAchievementsByUserId(userId));
    }

    @Test
    public void testGetAchievementsByUserIdSuccessful() {
        long userId = 1L;

        achievementService.getAchievementsByUserId(userId);

        verify(userAchievementRepository, times(1)).findByUserId(userId);
    }

    @Test
    public void testGetAchievementIfAchievementNotFound() {
        long id = 1L;
        when(achievementRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> achievementService.getAchievement(id));
    }

    @Test
    public void testGetAchievementSuccessful() {
        long id = 1L;
        Achievement achievement = new Achievement();
        AchievementDto dto = prepareAchievementDto();
        when(achievementRepository.findById(id)).thenReturn(Optional.of(achievement));
        when(achievementMapperMock.toDto(achievement)).thenReturn(dto);


        AchievementDto testResult = achievementService.getAchievement(id);

        verify(achievementRepository, times(1)).findById(id);
        assertEquals(testResult, dto);
    }

    @Test
    public void testGetAllAchievementsProgressForUserIfNoAchievementsProgressFound() {
        long userId = 1L;
        when(achievementProgressRepository.findByUserId(userId)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> achievementService.getAllAchievementsProgressForUser(userId));
    }

    @Test
    public void testGetAllAchievementsProgressForUserSuccessful() {
        long userId = 1L;

        achievementService.getAllAchievementsProgressForUser(userId);

        verify(achievementProgressRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testUserHasAchievement() {
        achievementService.userHasAchievement(1, 1);

        verify(userAchievementRepository, times(1)).existsByUserIdAndAchievementId(1, 1);
    }

    @Test
    void testCreateProgressIfNecessary() {
        achievementService.createProgressIfNecessary(1, 1);

        verify(achievementProgressRepository, times(1))
                .createProgressIfNecessary(1, 1);
    }

    @Test
    void testGetProgress_exist_returns() {
        AchievementProgress progress = AchievementProgress.builder()
                .id(123)
                .build();

        when(achievementProgressRepository.findByUserIdAndAchievementId(1, 1))
                .thenReturn(Optional.of(progress));

        AchievementProgress result = achievementService.getProgress(1, 1);

        assertEquals(progress, result);
        verify(achievementProgressRepository, times(1))
                .findByUserIdAndAchievementId(1, 1);
    }

    @Test
    void testGetProgress_notExist_throws() {
        when(achievementProgressRepository.findByUserIdAndAchievementId(1, 1))
                .thenReturn(Optional.empty());


        assertThrows(NotFoundException.class, () -> achievementService.getProgress(1, 1));
        verify(achievementProgressRepository, times(1))
                .findByUserIdAndAchievementId(1, 1);
    }

    @Test
    void testSaveProgress() {
        AchievementProgress progress = AchievementProgress.builder()
                .id(123)
                .build();

        achievementService.saveProgress(progress);

        verify(achievementProgressRepository, times(1))
                .save(progress);
    }

    @Test
    void testGiveAchievement() {
        Achievement achievement1 = Achievement.builder()
                .id(123)
                .build();

        when(userAchievementMapper.toEvent(any())).thenReturn(AchievementEvent.builder().build());

        achievementService.giveAchievement(1, achievement1);

        verify(userAchievementRepository, times(1))
                .save(any(UserAchievement.class));
        verify(achievementPublisher, times(1))
                .publishMessage(any(AchievementEvent.class));
    }

}
