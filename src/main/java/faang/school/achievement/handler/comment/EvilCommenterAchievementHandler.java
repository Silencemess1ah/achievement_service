package faang.school.achievement.handler.comment;

import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EvilCommenterAchievementHandler extends CommentEventHandler {

    private final AchievementService achievementService;
    //todo общую логику по обработке от слушателя
    //todo возможные сценарии работы с кэшем и сервисом
    @Async
    public void verifyAchievement(){

    }
}
