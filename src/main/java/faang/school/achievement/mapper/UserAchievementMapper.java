package faang.school.achievement.mapper;

import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.model.UserAchievement;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = AchievementMapper.class)
public interface UserAchievementMapper {
    UserAchievementDto toDto(UserAchievement achievement);

    List<UserAchievementDto> toDtoList(List<UserAchievement> achievements);
}
