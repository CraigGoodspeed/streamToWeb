package za.co.goodspeed.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class StreamToWeb {

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
        Files.readAllLines(Paths.get(fileName)).forEach( s -> toReturn.append(String.join("<br/>",s)));

        FileWriter writer = new FileWriter(fileName, false);
        writer.write("");
        writer.close();
        return toReturn.toString();
    }
}
