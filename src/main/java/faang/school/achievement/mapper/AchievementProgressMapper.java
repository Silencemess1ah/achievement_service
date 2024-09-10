package faang.school.achievement.mapper;

import faang.school.achievement.dto.AchievementProgressDto;
import faang.school.achievement.model.AchievementProgress;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AchievementMapper.class})
public interface AchievementProgressMapper {

    AchievementProgressDto toDto(AchievementProgress achievementProgress);
    List<AchievementProgressDto> toDtoList(List<AchievementProgress> achievementProgressList);
}
