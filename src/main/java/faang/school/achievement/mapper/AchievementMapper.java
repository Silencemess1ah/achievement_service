package faang.school.achievement.mapper;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.model.Achievement;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UserAchievementMapper.class, AchievementProgressMapper.class})
public interface AchievementMapper {
    AchievementDto toDto(Achievement achievement);

    Achievement toEntity(AchievementDto achievementDto);
}
