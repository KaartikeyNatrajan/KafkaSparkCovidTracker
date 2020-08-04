package com.sparkapp.covidtracker;

import com.google.gson.Gson;
import com.sparkapp.covidtracker.models.Case;
import com.sparkapp.covidtracker.models.IndiaCase;
import com.sparkapp.covidtracker.models.UsaCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

@Service
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(KafkaProperties.Producer.class);

    @KafkaListener(topics = "usa-topic", groupId = "group_id")
    public void consumeUsa(String message) throws IOException {
        Path filePath = Paths.get("data.json");
        UsaCase usacase = new Gson().fromJson(message, UsaCase.class);
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMdd");
        Date mydate = null;
        try {
            mydate = myFormat.parse("" + usacase.date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Case finalcase = new Case();

        finalcase.country = "USA";
        finalcase.date = mydate;
        finalcase.cases = usacase.positiveIncrease;
        finalcase.deaths = usacase.deathIncrease;

        String caseAsJson = new Gson().toJson(finalcase);
        caseAsJson += "\n";
        Files.write(filePath, caseAsJson.getBytes(), StandardOpenOption.APPEND);
    }

    @KafkaListener(topics = "india-topic", groupId = "group_id")
    public void consumeIndia(String message) throws IOException {
        Path filePath = Paths.get("data.json");
        IndiaCase indiacase = new Gson().fromJson(message, IndiaCase.class);

        SimpleDateFormat myFormat = new SimpleDateFormat("dd MMMM");
        Date mydate = null;
        try {
            mydate = myFormat.parse(indiacase.date.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Case finalcase = new Case();

        finalcase.country = "India";
        finalcase.date = mydate;
        finalcase.cases = indiacase.dailyconfirmed;
        finalcase.deaths = indiacase.dailydeceased;

        String caseAsJson = new Gson().toJson(finalcase);
        caseAsJson += "\n";
        Files.write(filePath, caseAsJson.getBytes(), StandardOpenOption.APPEND);
    }

}