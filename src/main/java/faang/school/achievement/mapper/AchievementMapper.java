package faang.school.achievement.mapper;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AchievementMapper {

    AchievementDto toAchievementDto(Achievement achievement);

    @Mapping(source = "achievement", target = "achievement", qualifiedByName = "achievementMap")
    AchievementProgressDto toAchievementProgressDto(AchievementProgress achievementProgress);

    AchievementProgress toAchievementProgress(AchievementProgressDto dto);

    @Named("achievementMap")
    default AchievementDto achievementToAchievementDto(Achievement achievement) {
        return toAchievementDto(achievement);
    }
}
