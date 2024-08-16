package faang.school.achievement.publisher.achievement;

import faang.school.achievement.event.achievement.AchievementEvent;
import faang.school.achievement.eventhandler.EventHandler;
import faang.school.achievement.listener.achievement.AchievementListener;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AchievementPublisherTest {

    private final long timeoutMillis = 5000;

    @Autowired
    private AchievementPublisher achievementPublisher;

    @Autowired
    private AchievementListener achievementListener;

    final Object lock = new Object();

    private AchievementEvent obtainedAchievementEvent;

    @BeforeEach
    void setUp() {

        EventHandler<AchievementEvent> onEvent = e -> {
            synchronized (lock) {
                obtainedAchievementEvent = e;
                lock.notifyAll();
            }
        };

        List<EventHandler<AchievementEvent>> eventHandlers = List.of(onEvent);
        ReflectionTestUtils.setField(achievementListener, "handlers", eventHandlers);
    }

    @AfterEach
    void avoid() {
        obtainedAchievementEvent = null;
    }

    @Test
    void publishEvent() throws InterruptedException {

        var event = new AchievementEvent(
                1L,
                1L,
                1L,
                LocalDateTime.now().withNano(0)
        );

        achievementPublisher.publish(event);

        synchronized (lock) {
            lock.wait(timeoutMillis);
        }

        assertEquals(obtainedAchievementEvent, event);
    }
}
