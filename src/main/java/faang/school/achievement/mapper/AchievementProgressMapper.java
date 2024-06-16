package faang.school.achievement.mapper;

import faang.school.achievement.dto.achievement.AchievementProgressDto;
import faang.school.achievement.model.AchievementProgress;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {AchievementMapper.class})
public interface AchievementProgressMapper {

    AchievementProgressDto toDto(AchievementProgress achievementProgress);

    AchievementProgress toEntity(AchievementProgressDto achievementProgressDto);
}
