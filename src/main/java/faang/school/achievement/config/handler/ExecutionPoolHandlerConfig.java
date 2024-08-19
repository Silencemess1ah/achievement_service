package faang.school.achievement.config.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
@RequiredArgsConstructor
public class ExecutionPoolHandlerConfig {
    @Value("${handler.executor_pool_parameters.core_pool_size}")
    private int corePoolSize;
    @Value("${handler.executor_pool_parameters.max_pool_capacity}")
    private int maxPoolCapacity;
    @Value("${handler.executor_pool_parameters.queue_capacity}")
    private int queueCapacity;
    @Value("${handler.executor_pool_parameters.thread_name_prefix}")
    private String threadNamePrefix;

    @Bean("achievementHandlerTaskExecutor")
    public ThreadPoolTaskExecutor achievementHandlerTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolCapacity);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.initialize();
        return executor;
    }
}