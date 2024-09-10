package faang.school.achievement.mapper;

import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.dto.UserAchievementEvent;
import faang.school.achievement.model.UserAchievement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * @author Evgenii Malkov
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, uses = {AchievementMapper.class})
public interface UserAchievementMapper {

    UserAchievementDto toDto(UserAchievement achievement);

    List<UserAchievementDto> toListDto(List<UserAchievement> achievement);

    @Mapping(source = "achievement.id", target = "achievementId")
    @Mapping(source = "achievement.title", target = "achievementTitle")
    UserAchievementEvent toEvent(UserAchievement userAchievement);

}
