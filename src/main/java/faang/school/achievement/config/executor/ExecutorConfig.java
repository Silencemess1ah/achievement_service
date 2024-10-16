package faang.school.achievement.config.executor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@RequiredArgsConstructor
public class ExecutorConfig {
    @Value("${thread-pool.size}")
    private int threadsNum;

    @Bean(name = "executorService")
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(threadsNum);
    }
}
