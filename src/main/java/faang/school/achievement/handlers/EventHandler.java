package faang.school.achievement.handlers;

import faang.school.achievement.dto.SkillAcquiredEvent;

public interface EventHandler {
    void handleAchievement(SkillAcquiredEvent event);
}
