package faang.school.achievement.config.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ThreadPoolConfig {

    @Bean
    public ExecutorService commentEventPool() {
        return Executors.newCachedThreadPool();
    }
}