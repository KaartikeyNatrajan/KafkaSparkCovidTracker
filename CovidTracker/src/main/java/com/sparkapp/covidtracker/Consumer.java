package com.sparkapp.covidtracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(KafkaProperties.Producer.class);

    @KafkaListener(topics = "test-topic", groupId = "group_id")
    public void consume(String message) throws IOException {
//        logger.info(String.format("#### -> Consumed message -> %s", message));
        Path filePath = Paths.get("data.json");
        message += "\n";
        Files.write(filePath, message.getBytes(), StandardOpenOption.APPEND);
    }


}
