package faang.school.achievement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

/**
 * @author Evgenii Malkov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AchievementFilterDto {

    private String titlePattern;
    private String descriptionPattern;
    private String rarityPattern;
    private SortField sortField = SortField.CREATED_AT;
    private Sort.Direction direction = Sort.Direction.DESC;
    private Integer page = 0;
    private Integer size = 10;
}
