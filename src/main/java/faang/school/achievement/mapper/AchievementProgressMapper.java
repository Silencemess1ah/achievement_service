package faang.school.achievement.mapper;

import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AchievementProgressMapper {

    @Mapping(source = "achievement", target = "achievementId", qualifiedByName = "achievementToId")
    AchievementProgressDto toDto(AchievementProgress achievementProgress);

    List<AchievementProgressDto> toDtos(List<AchievementProgress> achievementsProgress);

    @Named("achievementToId")
    default Long achievementToId(Achievement achievement) {
        return achievement.getId();
    }
}