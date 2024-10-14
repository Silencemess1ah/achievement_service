package faang.school.achievement.handler;

import faang.school.achievement.dto.ProjectEvent;

public interface AchievementHandler {
    void handleAchievement(ProjectEvent event);
}
