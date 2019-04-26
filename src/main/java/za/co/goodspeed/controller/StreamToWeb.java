package za.co.goodspeed.controller;

import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StreamToWeb {

    @Value("${outputFile}")
    String fileName;

    @RequestMapping(value="/")
    public String defaultCall(){
        return "hello world";
    }

    Flux<String> kafkaData = Flux.empty();
    List<String> currentUnsent = new ArrayList<>();




    @SneakyThrows
    @RequestMapping(value="/getKafka", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getKafkaData(){

        /*Optional<String> toReturn = Files.lines(Paths.get(fileName)).reduce((s1, s2) -> String.join("<br/>",s1,s2));
        if(toReturn.isPresent()) {
            FileWriter writer = new FileWriter(fileName, false);
            writer.write("");
            writer.close();
            return toReturn.get();
        }
        return "";*/
        /*StreamingOutput stream  = new StreamingOutput() {
            @Override
            public void write(OutputStream outputStream) throws IOException, WebApplicationException {
                Writer writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                writer.write(dataToSend.stream().reduce((s1,s2) -> String.join("<br/>",s1,s2)).get());
                writer.flush();
            }
        };
        Response.ok()
                .header("ContentType","text/event-stream")
                .entity(stream)
                .build();
        return Response.ok(stream).build();*/
        List<String> tmp = new ArrayList<>();
        currentUnsent.forEach(s -> tmp.add(s));
        Flux<String> toReturn = Flux.fromIterable(tmp);
        currentUnsent.clear();
        return toReturn;

    }
    @KafkaListener(topics={"streamToWeb"}, beanRef = "consumer", groupId = "streamToWeb")
    public void writeToFile(ConsumerRecord<String,String> record){
        currentUnsent.add(record.value());

    }
}
