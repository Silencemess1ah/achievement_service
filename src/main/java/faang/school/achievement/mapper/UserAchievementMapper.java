package faang.school.achievement.mapper;

import faang.school.achievement.dto.achievement.UserAchievementDto;
import faang.school.achievement.model.UserAchievement;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {AchievementMapper.class})
public interface UserAchievementMapper {

    UserAchievementDto toDto(UserAchievement achievement);

    UserAchievement toEntity(UserAchievementDto achievementDto);
}
