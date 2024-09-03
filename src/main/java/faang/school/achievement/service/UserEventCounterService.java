package faang.school.achievement.service;

import faang.school.achievement.model.EventType;
import faang.school.achievement.model.UserEventCounter;
import faang.school.achievement.repository.UserEventCounterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserEventCounterService {
    private final UserEventCounterRepository userEventCounterRepository;

    @Transactional
    public void addNewAllProgress(Long userId, EventType eventType) {
        UserEventCounter eventCounter = UserEventCounter.builder()
                .userId(userId)
                .allCurrentPoints(1)
                .eventType(eventType).build();
        userEventCounterRepository.save(eventCounter);
    }

    @Transactional
    public void updateAllProgress(Long userId, EventType eventType) {
        UserEventCounter userEventCounter = userEventCounterRepository.findByUserIdAndEventType(userId, eventType);
        userEventCounter.increment();
        userEventCounterRepository.save(userEventCounter);
    }

    @Transactional(readOnly = true)
    public boolean hasProgress(Long userId, EventType eventType) {
        return userEventCounterRepository.existsByUserIdAndEventType(userId, eventType);
    }

    @Transactional(readOnly = true)
    public Long getProgress(Long userId, EventType eventType) {
        return userEventCounterRepository.findCurrentPointsByUserIdAndEventType(userId, eventType);
    }


}
