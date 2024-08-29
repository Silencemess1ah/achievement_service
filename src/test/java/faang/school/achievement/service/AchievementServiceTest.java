package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementFilterDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.filter.AchievementFilter;
import faang.school.achievement.filter.impl.AchievementDescriptionFilter;
import faang.school.achievement.filter.impl.AchievementRarityFilter;
import faang.school.achievement.filter.impl.AchievementTitleFilter;
import faang.school.achievement.mapper.AchievementMapperImpl;
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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {
    private static final String MESSAGE_DOES_NOT_HAVE_ACHIEVEMENT = "The user does not have such an achievement";
    @Mock
    private AchievementRepository achievementRepository;
    @Mock
    private AchievementProgressRepository achievementProgressRepository;
    @Mock
    private UserAchievementRepository userAchievementRepository;
    @Mock
    private MessageSource messageSource;
    @Spy
    private AchievementMapperImpl mapper;
    @Spy
    private AchievementTitleFilter titleFilter;
    @Spy
    private AchievementDescriptionFilter descriptionFilter;
    @Spy
    private AchievementRarityFilter rarityFilter;
    private AchievementService service;

    private AchievementTestContainer container;

    @BeforeEach
    void setUp() {
        container = new AchievementTestContainer();
        List<AchievementFilter> achievementFilters = new ArrayList<>(List.of(titleFilter, descriptionFilter, rarityFilter));
        service = new AchievementService(achievementRepository, achievementProgressRepository,
                userAchievementRepository, mapper, achievementFilters, messageSource);
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
    void testValidHasAchievement() {
        // when
        when(userAchievementRepository.existsByUserIdAndAchievementId(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(true);
        // then
        assertTrue(service.hasAchievement(container.userId(), container.achievementId()));
    }

    @Test
    void testInvalidHasAchievement() {
        // when
        when(userAchievementRepository.existsByUserIdAndAchievementId(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(false);

        // then
        assertFalse(service.hasAchievement(container.userId(), container.achievementId()));
    }

    @Test
    void testGetProgressIfExist() {
        // given
        AchievementProgress achievementProgress = container.achievementProgress();
        AchievementProgressDto progressDto = mapper.toAchievementProgressDto(achievementProgress);
        // when
        when(achievementProgressRepository.findByUserIdAndAchievementId(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(Optional.of(achievementProgress));
        // then
        assertEquals(progressDto,
                service.getProgress(Mockito.anyLong(), Mockito.anyLong()));
    }

    @Test
    void testGetProgressIfNotExist() {
        // when
        when(achievementProgressRepository.findByUserIdAndAchievementId(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(Optional.empty());
        when(messageSource.getMessage(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(MESSAGE_DOES_NOT_HAVE_ACHIEVEMENT);
        // then
        assertEquals(MESSAGE_DOES_NOT_HAVE_ACHIEVEMENT,
                assertThrows(RuntimeException.class, () ->
                        service.getProgress(Mockito.anyLong(), Mockito.anyLong())).getMessage());
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