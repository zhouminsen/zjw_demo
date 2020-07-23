package com.cjlogistics.barcode.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2020-05-20.
 */
@RequestMapping("/student")
@RestController
public class ScanController {


    @GetMapping("/helloWorld")
    public Object helloWorld(@RequestParam String code) {

        return "haha";
    }
}
