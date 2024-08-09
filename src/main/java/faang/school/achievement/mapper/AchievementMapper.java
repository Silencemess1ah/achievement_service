package faang.school.achievement.mapper;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AchievementMapper {

    @Mapping(source = "userAchievements", target = "userAchievementIds", qualifiedByName = "mapUserAchievements")
    @Mapping(source = "progresses", target = "progressIds", qualifiedByName = "mapProgresses")
    AchievementDto toDto(Achievement achievement);

    List<AchievementDto> toDtoList(List<Achievement> achievements);

    @Named("mapUserAchievements")
    default List<Long> mapUserAchievements(List<UserAchievement> userAchievements) {
        return userAchievements.stream().map(UserAchievement::getId).toList();
    }

    @Named("mapProgresses")
    default List<Long> mapProgresses(List<AchievementProgress> progresses) {
        return progresses.stream().map(AchievementProgress::getId).toList();
    }
}
