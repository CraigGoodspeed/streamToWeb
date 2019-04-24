package za.co.goodspeed.controller;

import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Optional;

@Component
public class KafkaInput {

    @Value("${outputFile}")
    String fileName;


    @KafkaListener(topics={"streamToWeb"}, beanRef = "consumer", groupId = "streamToWeb")
    public void writeToFile(ConsumerRecord<String,String> record){
        Optional<String> message = Optional.ofNullable(record.value());
        if(message.isPresent()){
            writeToTmp(message.get());
        }
    }
    @SneakyThrows
    private void writeToTmp(String message){
        BufferedWriter fw = new BufferedWriter(new FileWriter(fileName, true));
        fw.write(message);
        fw.newLine();
        fw.close();
    }
}
