package faang.school.achievement.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.exception.DataNotFoundException;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.repository.AchievementRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class AchievementCache {

    private final AchievementRepository achievementRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private final SessionFactory sessionFactory;

    @PostConstruct
    public void initCache() {
        List<Achievement> allAchievements = getAllAchievements();

        allAchievements.forEach(achievement -> {
            try {
                String achievementJson = objectMapper.writeValueAsString(achievement);
                redisTemplate.opsForValue().set(achievement.getTitle(), achievementJson);
            } catch (Exception e) {
                log.error("Error serializing achievement to JSON", e);
            }
        });
    }

    public List<Achievement> getAllAchievements() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Achievement> achievements = session.createQuery("from Achievement", Achievement.class).getResultList();

        for (Achievement achievement : achievements) {
            Hibernate.initialize(achievement.getUserAchievements());
            Hibernate.initialize(achievement.getProgresses());
            Hibernate.initialize(achievement.getRarity());
        }

        session.getTransaction().commit();
        session.close();

        return achievements;
    }

    public Achievement getAchievementByTitle(String title) {
        String achievement = (String) redisTemplate.opsForValue().get(title);

        if (achievement == null) {
            achievementRepository.findByTitle(title).orElseThrow(() -> new DataNotFoundException("Achievement not found " + title));
        }
        try {
            return objectMapper.readValue(achievement, Achievement.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

/*    @Scheduled(cron = "${cache.achievement.update-schedule}")
    public void updateCache() {
        log.info("Starting update of Achievement cache");
        this.initCache();
    }*/
}
