package faang.school.achievement.config.kafka;

import lombok.Data;

@Data
public class Topic {
    private String name;
    private int numPartitions;
    private short replicationFactor;
}
