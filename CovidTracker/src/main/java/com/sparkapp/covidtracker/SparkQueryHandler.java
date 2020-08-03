package com.sparkapp.covidtracker;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
class SparkQueryHandler {
    List<Case> runQuery() {
        SparkSession spark = SparkSession
                .builder()
                .appName("Java Spark SQL data sources example")
                .config("spark.some.config.option", "some-value")
                .config("spark.app.name", "TEST APP")
                .config("spark.master", "local[2]")
                .getOrCreate();

        List<Case> names = runJsonDatasetExample(spark);
        spark.stop();
        return names;
    }

    private static List<Case> runJsonDatasetExample(SparkSession spark) {
        Dataset<Row> people = spark.read().json("data.json");

        people.printSchema();
        people.createOrReplaceTempView("data");

        Dataset<Row> namesDF = spark.sql("SELECT * FROM data WHERE deaths = 1;");
        namesDF.show();
        List<Case> cases = new ArrayList<>();
        namesDF
                .toJSON()
                .collectAsList()
                .forEach((data -> cases.add(new Gson().fromJson(data, Case.class))));
        return cases;
    }
}