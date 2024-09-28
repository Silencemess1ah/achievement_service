package faang.school.achievement.mapper;

import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.model.AchievementProgress;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * @author Evgenii Malkov
 */
@Mapper(componentModel = "spring", uses = {AchievementMapper.class}, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AchievementProgressMapper {

    AchievementProgressDto toDto(AchievementProgress achievement);

    List<AchievementProgressDto> toListDto(List<AchievementProgress> achievement);
}
