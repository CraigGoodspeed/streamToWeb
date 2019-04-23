package za.co.goodspeed.controller;

import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.goodspeed.controller.util.Consumer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

@RestController
public class StreamToWeb {

    @Autowired
    Consumer consumer;

    @Value("${outputFile}")
    String fileName;

    @RequestMapping(value="/")
    public String defaultCall(){
        return "hello world";
    }

    @SneakyThrows
    @RequestMapping(value="/getKafka")
    public String getKafkaData(){
        StringBuilder toReturn = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        reader.lines().forEach(s -> toReturn.append(s));
        reader.close();

        FileWriter writer = new FileWriter(fileName, false);
        writer.write("");
        writer.close();
        return toReturn.toString();
    }
}
