package faang.school.achievement.config.async;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@RequiredArgsConstructor
public class TaskExecutorConfig {

    private final ExecutorProperties executorProperties;

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(executorProperties.getCoreSize());
        executor.setMaxPoolSize(executorProperties.getMaxSize());
        executor.setQueueCapacity(executorProperties.getQueueCapacity());
        executor.initialize();
        return executor;
    }
}
