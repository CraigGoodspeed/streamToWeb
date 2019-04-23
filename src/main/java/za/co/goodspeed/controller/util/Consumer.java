package za.co.goodspeed.controller.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import java.util.Properties;

@Data
public class Consumer {
    @Value("${topicName}")
    String topicName;
    @Value("${consumer.bootstrap.servers}")
    String bootStrapServer;
    @Value("${consumer.enable.auto.commit}")
    String autoCommit;
    @Value("${consumer.auto.commit.interval.ms}")
    String autoCommitInterval;
    @Value("${consumer.session.timeout.ms}")
    String sessionTimeout;
    @Value("${consumer.key.deserializer}")
    String keyDeserializer;
    @Value("${consumer.value.deserializer}")
    String valueDeserializer;
    @Value("${consumer.group.id}")
    String groupId;

    private Properties properties;

    public Properties getProperties(){
        if(properties == null) {
            properties = new Properties();
            properties.put("bootstrap.servers", getBootStrapServer());
            properties.put("enable.auto.commit", getAutoCommit());
            properties.put("auto.commit.interval.ms", getAutoCommitInterval());
            properties.put("session.timeout.ms", getSessionTimeout());
            properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            properties.put("group.id", getGroupId());
            properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        }
        return properties;
    }
}
