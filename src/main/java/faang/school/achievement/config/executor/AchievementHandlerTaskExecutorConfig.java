package faang.school.achievement.config.executor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@RequiredArgsConstructor
public class AchievementHandlerTaskExecutorConfig {

    @Value("${handler.thread-pool-executor-parameters.core-pool-size}")
    private int corePoolSize;

    @Value("${handler.thread-pool-executor-parameters.max-pool-size}")
    private int maxPoolSize;

    @Value("${handler.thread-pool-executor-parameters.queue-capacity}")
    private int queueCapacity;

    @Value("${handler.thread-pool-executor-parameters.await-termination-seconds}")
    private int awaitTerminationSeconds;

    @Bean("achievementHandlerTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(awaitTerminationSeconds);

        executor.initialize();
        return executor;
    }
}
