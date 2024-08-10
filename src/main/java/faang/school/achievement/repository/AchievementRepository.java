package faang.school.achievement.repository;

import faang.school.achievement.exception.EntityNotFoundException;
import faang.school.achievement.model.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    default Achievement getById(long id) {
        return findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Achievement with ID: %d not found", id)));
    }
}
