package com.cjlogistics.barcode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2020-05-20.
 */
@RequestMapping("")
@Controller
public class ScanController {


    @GetMapping("")
    public String index() {

        return "index";
    }

    @GetMapping("/getData")
    @ResponseBody
    public Map<String, Object> getData(@RequestParam String code) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("id2", 2);
        map.put("id3", 3);
        map.put("id4", 4);
        map.put("id5", code);
        return map;
    }
}
