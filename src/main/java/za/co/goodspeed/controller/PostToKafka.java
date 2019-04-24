package za.co.goodspeed.controller;

import lombok.SneakyThrows;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import za.co.goodspeed.controller.util.FileChangedWatcher;
import za.co.goodspeed.controller.util.ProducerConfig;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

@Component
public class PostToKafka extends FileChangedWatcher {

    @Value("${inputFile}")
    String inputFile;

    @Value("${topicName}")
    String topicName;

    @Autowired
    ProducerConfig config;

    @PostConstruct
    @SneakyThrows
    public void run(){
        createMe(inputFile, 500);
        watch();
    }

    @Override
    @SneakyThrows
    protected void onModified() {
        try (
            KafkaProducer producer = new KafkaProducer(config.getProperties());
            BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
            reader.lines().forEach(s -> {
                System.out.println(String.format("writing %s to kafka",s));
                producer.send(new ProducerRecord(topicName, s));
            });
        }
        FileWriter writer = new FileWriter(inputFile);
        writer.write("");
        writer.close();
    }
}
