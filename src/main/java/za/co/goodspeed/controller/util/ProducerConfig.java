package za.co.goodspeed.controller.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Data
@Component
public class ProducerConfig {

    @Value("${producer.bootstrap.servers}")
    String bootStrapServer;
    @Value("${producer.acks}")
    String acknowledge;
    @Value("${producer.retries}")
    String retries;

    @Value("${producer.batch.size}")
    String batchSize;

    @Value("${producer.linger.ms}")
    String lingerMillis;
    @Value("${producer.buffer.memory}")
    String bufferMemory;
    @Value("${producer.key.serializer}")
    String keySerializer;
    @Value("${producer.value.serializer}")
    String valueSerializer;

    private static Properties toReturn;
    public Properties getProperties(){

        if(toReturn == null) {
            toReturn = new Properties();
            toReturn.put("bootstrap.servers", getBootStrapServer());
            toReturn.put("acks", getAcknowledge());
            toReturn.put("retries", getRetries());
            toReturn.put("batch.size", getRetries());
            toReturn.put("linger.ms", getLingerMillis());
            toReturn.put("buffer.memory", getBufferMemory());
            toReturn.put("key.serializer", getKeySerializer());
            toReturn.put("value.serializer", getValueSerializer());
        }
        return toReturn;
    }


}