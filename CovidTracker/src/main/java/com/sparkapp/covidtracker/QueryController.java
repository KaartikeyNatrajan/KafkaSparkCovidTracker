package com.sparkapp.covidtracker;
import com.google.gson.Gson;
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