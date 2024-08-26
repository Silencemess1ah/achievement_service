package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.filter.AchievementFilter;
import faang.school.achievement.filter.impl.AchievementDescriptionFilter;
import faang.school.achievement.filter.impl.AchievementRarityFilter;
import faang.school.achievement.filter.impl.AchievementTitleFilter;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.Rarity;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import faang.school.achievement.util.AchievementTestContainer;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {
    @Mock
    private AchievementRepository achievementRepository;
    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Spy
    private AchievementMapper mapper;
    @Spy
    private AchievementTitleFilter titleFilter;
    @Spy
    private AchievementDescriptionFilter descriptionFilter;
    @Spy
    private AchievementRarityFilter rarityFilter;
    @InjectMocks
    private AchievementService service;

    private AchievementTestContainer container;

    @BeforeEach
    void setUp() {
        container = new AchievementTestContainer();
        List<AchievementFilter> achievementFilters = new ArrayList<>(List.of(titleFilter, descriptionFilter, rarityFilter));
        service = new AchievementService(achievementRepository, achievementProgressRepository,
                userAchievementRepository, mapper, achievementFilters);
    }


    @Test
    void testGetExistingAchievement() {
        // given
        Long achievementId = container.achievementId();
        Achievement entity = container.achievement();
        AchievementDto dtoExp = mapper.toAchievementDto(entity);
        when(achievementRepository.findById(achievementId)).thenReturn(Optional.of(entity));

        // when
        AchievementDto dtoActual = service.getAchievement(achievementId);

        // then
        assertEquals(dtoExp, dtoActual);
    }

    @Test
    void testGetNonExistingAchievement() {
        // given
        Long achievementId = container.achievementId();
        when(achievementRepository.findById(achievementId)).thenThrow(EntityNotFoundException.class);

        // then
        assertThrows(EntityNotFoundException.class, () -> service.getAchievement(achievementId));
    }

    @Test
    void testGetUserAchievements() {
        // given
        Long userId = container.userId();
        UserAchievement entity = container.userAchievement();
        AchievementDto dtoExp = mapper.toAchievementDto(entity.getAchievement());
        when(userAchievementRepository.findByUserId(userId)).thenReturn(List.of(entity));

        // when
        List<AchievementDto> listDtoActual = service.getUserAchievements(userId);

        // then
        assertEquals(dtoExp, listDtoActual.get(0));
    }

    @Test
    void getAchievementsProgress() {
        // given
        Long userId = container.userId();
        AchievementProgress entity = container.achievementProgress();
        AchievementProgressDto dtoExp = mapper.toAchievementProgressDto(entity);
        when(achievementProgressRepository.findByUserId(userId)).thenReturn(List.of(entity));

        // when
        List<AchievementProgressDto> listDtoActual = service.getAchievementsProgress(userId);

        // then
        assertEquals(dtoExp, listDtoActual.get(0));
    }

    @Test
    void testGetAchievementsWithFilters() {
        AchievementFilterDto filters = container.filters();
        List<Achievement> achievementList = getAchievements(filters.getTitle(), filters.getDescription(), filters.getRarity());
        when(achievementRepository.findAll()).thenReturn(achievementList);
        AchievementDto dtoExp = mapper.toAchievementDto(achievementList.get(3));
        int sizeExp = 1;

        // when
        List<AchievementDto> listDtoActual = service.getAchievements(filters);

        // then
        assertEquals(sizeExp, listDtoActual.size());
        assertEquals(dtoExp, listDtoActual.get(0));
    }

    @Test
    void testGetAchievementsWithOutFilters() {
        AchievementFilterDto filters = new AchievementFilterDto();
        List<Achievement> achievementList = List.of(container.achievement());
        int sizeExp = 1;
        when(achievementRepository.findAll()).thenReturn(achievementList);

        // when
        List<AchievementDto> listDtoActual = service.getAchievements(filters);

        // then
        assertEquals(sizeExp, listDtoActual.size());
    }

    @Test
    void testHasAchievement() {
        long userId = container.userId();
        long achievementId = container.achievementId();

        when(userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId)).thenReturn(true);
        assertTrue(service.hasAchievement(userId, achievementId));

        when(userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId)).thenReturn(false);
        assertFalse(service.hasAchievement(userId, achievementId));

        verify(userAchievementRepository, times(2))
                .existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Test
    void testCreateProgressIfNecessary() {
        long userId = container.userId();
        long achievementId = container.achievementId();

        service.createProgressIfNecessary(userId, achievementId);
        verify(achievementProgressRepository, times(1))
                .createProgressIfNecessary(userId, achievementId);
    }

    @Test
    void testGetProgressWhenExists() {
        long followeeId = container.userId();
        long achievementId = container.achievementId();
        AchievementProgress progress = AchievementProgress.builder()
                .id(1L)
                .userId(followeeId)
                .currentPoints(10L)
                .build();
        Optional<AchievementProgress> progressOptional = Optional.of(progress);
        when(achievementProgressRepository.findByUserIdAndAchievementId(followeeId, achievementId))
                .thenReturn(progressOptional);

        assertEquals(progress, service.getProgress(followeeId, achievementId));
        verify(achievementProgressRepository, times(1))
                .findByUserIdAndAchievementId(followeeId, achievementId);
    }

    @Test
    void testGetProgressWhenNotExists() {
        long followeeId = container.userId();
        long achievementId = container.achievementId();
        Optional<AchievementProgress> progressOptional = Optional.empty();
        when(achievementProgressRepository.findByUserIdAndAchievementId(followeeId, achievementId))
                .thenReturn(progressOptional);

        assertThrows(EntityNotFoundException.class, () -> service.getProgress(followeeId, achievementId));
        verify(achievementProgressRepository, times(1))
                .findByUserIdAndAchievementId(followeeId, achievementId);
    }

    @Test
    void testSaveProgress() {
        AchievementProgress progress = AchievementProgress.builder()
                .id(1L)
                .userId(2L)
                .currentPoints(10L)
                .build();
        when(achievementProgressRepository.save(progress)).thenReturn(progress);

        assertEquals(progress, service.saveProgress(progress));
        verify(achievementProgressRepository, times(1)).save(progress);
    }

    @Test
    void testGiveAchievement() {
        Achievement achievement = new Achievement();
        long userId = container.userId();
        UserAchievement userAchievement = UserAchievement.builder()
                .achievement(achievement)
                .userId(userId)
                .build();

        service.giveAchievement(userId, achievement);
        verify(userAchievementRepository, times(1)).save(userAchievement);
    }

    private List<Achievement> getAchievements(String titleFilter, String descriptionFilter, Rarity rarityFilter) {
        Achievement achievementFirst = createAchievement(container.title(), descriptionFilter, rarityFilter);
        Achievement achievementSecond = createAchievement(titleFilter, container.description(), container.rarity());
        Achievement achievementThird = createAchievement(titleFilter, descriptionFilter, container.rarity());
        Achievement achievementFourth = createAchievement(titleFilter, descriptionFilter, rarityFilter);

        return List.of(achievementFirst, achievementSecond, achievementThird, achievementFourth);
    }

    private Achievement createAchievement(String title, String description, Rarity rarity) {
        return Achievement.builder()
                .title(title)
                .description(description)
                .rarity(rarity)
                .build();
    }
}