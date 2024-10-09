package faang.school.achievement.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("server")
public class ApplicationProperties {
    private int port;
    private int mainThreadPoolSize;
}
