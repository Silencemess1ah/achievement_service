package faang.school.achievement.config.thread.pool;

import faang.school.achievement.config.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
@RequiredArgsConstructor
public class ExecutorConfig {

    private final ApplicationProperties properties;

    @Bean
    public ExecutorService mainExecutorService() {
        return Executors.newFixedThreadPool(properties.getMainThreadPoolSize());
    }
}
