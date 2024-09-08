package faang.school.achievement.handler;

import faang.school.achievement.event.FollowerEvent;
import faang.school.achievement.processor.AchievementProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FollowerEventHandler implements EventHandler<FollowerEvent> {

    private final List<AchievementProcessor> achievementProcessors;

    @Override
    public void handleEvent(FollowerEvent event) {
        for (AchievementProcessor handler : achievementProcessors) {
            handler.processAchievement(event.getUserId());
        }
    }
}