package faang.school.achievement.mapper;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.achievement.UserAchievementEventDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.UserAchievement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AchievementMapper {

    @Mapping(source = "achievement.title", target = "title")
    @Mapping(source = "achievement.rarity", target = "rarity")
    @Mapping(source = "achievement.id", target = "achievementId")
    UserAchievementEventDto toUserAchievementEventDto(UserAchievement userAchievement);

    AchievementDto toDto(Achievement achievement);

    List<AchievementDto> toDtoList(List<Achievement> achievement);
}
