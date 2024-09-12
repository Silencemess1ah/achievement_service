package faang.school.achievement.mapper;

import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.model.AchievementProgress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {AchievementMapper.class},
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AchievementProgressMapper {

    AchievementProgressDto toDto(AchievementProgress achievementProgress);

    AchievementProgress toEntity(AchievementProgressDto achievementProgressDto);

    List<AchievementProgressDto> toListDto(List<AchievementProgress> achievementProgresses);

    List<AchievementProgress> toListEntity(List<AchievementProgressDto> achievementProgressDtos);
}
