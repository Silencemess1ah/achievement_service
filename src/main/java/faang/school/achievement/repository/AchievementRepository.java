package faang.school.achievement.repository;

import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.model.Achievement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends CrudRepository<Achievement, Long> {

    default Achievement getById(long id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException("Achievement with ID " + id + " not found"));
    }
}
