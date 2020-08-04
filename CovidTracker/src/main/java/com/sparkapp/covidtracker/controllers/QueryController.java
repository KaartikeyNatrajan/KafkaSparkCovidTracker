package com.sparkapp.covidtracker.controllers;

import com.google.gson.Gson;
import com.sparkapp.covidtracker.models.Case;
import com.sparkapp.covidtracker.models.CovidData;
import com.sparkapp.covidtracker.SparkQueryHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
public class QueryController {
    @RequestMapping("/api/v1/query")
    public String index() {
        SparkQueryHandler handler = new SparkQueryHandler();
        List<Case> names = handler.runQuery();
        CovidData data = new CovidData();
        data.cases = names;
        return new Gson().toJson(data);
    }
}