package faang.school.achievement.mapper;

import faang.school.achievement.dto.UserAchievementDto;
import faang.school.achievement.model.AchievementEvent;
import faang.school.achievement.model.UserAchievement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserAchievementMapper {

    @Mapping(source = "achievement.id", target = "achievementId")
    UserAchievementDto toDto(UserAchievement userAchievement);

    List<UserAchievementDto> toDtoList(List<UserAchievement> userAchievements);

    @Mapping(target = "id", source = "achievement.id")
    @Mapping(target = "title", source = "achievement.title")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "createdAt", source = "createdAt")
    AchievementEvent toEvent(UserAchievement userAchievement);
}
