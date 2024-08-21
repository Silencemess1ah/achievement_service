package faang.school.achievement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class ThreadPool {
    @Bean
    public ExecutorService cachedExecutorService() {
        return Executors.newCachedThreadPool();
    }

    @Bean
    public ExecutorService fixedExecutorService() {
        return Executors.newFixedThreadPool(10);
    }
}
