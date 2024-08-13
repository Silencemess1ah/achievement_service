package faang.school.achievement.repository;

import faang.school.achievement.model.Achievement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AchievementRepository extends CrudRepository<Achievement, Long> {
    boolean existsByTitle(String title);

    @Query("SELECT a FROM Achievement a LEFT JOIN a.userAchievements ua WHERE ua.userId = :userId")
    List<Achievement> findAllAchievementForUser(long id);
}
