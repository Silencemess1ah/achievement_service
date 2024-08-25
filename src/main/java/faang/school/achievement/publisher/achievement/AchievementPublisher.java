package faang.school.achievement.publisher.achievement;

import faang.school.achievement.dto.AchievementEventDto;

public interface AchievementPublisher {
    void publish(AchievementEventDto eventDto);
}
