package faang.school.achievement.mapper;

import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.model.AchievementProgress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {AchievementMapper.class})
public interface AchievementProgressMapper {

    @Mapping(source = "achievement", target = "achievementDto")
    AchievementProgressDto toDto(AchievementProgress achievementProgress);
}
