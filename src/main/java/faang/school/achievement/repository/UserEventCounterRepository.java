package faang.school.achievement.repository;

import faang.school.achievement.model.EventType;
import faang.school.achievement.model.UserEventCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEventCounterRepository extends JpaRepository<UserEventCounter, Long> {

    UserEventCounter findByUserIdAndEventType(Long userId, EventType eventType);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM UserEventCounter u " +
            "WHERE u.userId = :userId AND u.eventType = :eventType")
    boolean existsByUserIdAndEventType(long userId, EventType eventType);

    @Query("SELECT u.allCurrentPoints FROM UserEventCounter u WHERE u.userId = :userId AND u.eventType = :eventType")
    Long findCurrentPointsByUserIdAndEventType(@Param("userId") Long userId, @Param("eventType") EventType eventType);
}
