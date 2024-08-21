package faang.school.achievement.mapper;

import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.model.UserAchievement;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * @author Evgenii Malkov
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, uses = {AchievementMapper.class})
public interface UserAchievementMapper {

    UserAchievementDto toDto(UserAchievement achievement);

    List<UserAchievementDto> toListDto(List<UserAchievement> achievement);

}
