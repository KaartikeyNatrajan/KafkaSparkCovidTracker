package com.sparkapp.covidtracker;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UIController {
    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public String index() {
        System.out.println("ASDASDASDASDASDASDASDASDASDASDASDASDASDASDASDASD");
        return "index";
    }
}
