package faang.school.achievement.eventHandler;

import faang.school.achievement.model.CommentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@Component
public abstract class CommentEventHandler implements EventHandler<CommentEvent> {



    @Bean
    public ExecutorService commentEventTPool() {
        return Executors.newCachedThreadPool();
    }

}
