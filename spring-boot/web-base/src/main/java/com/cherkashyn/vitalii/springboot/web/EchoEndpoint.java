package com.cherkashyn.vitalii.springboot.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoEndpoint {

    @RequestMapping("/echo")
    public String greeting(@RequestParam(value="name", defaultValue="string from client") String value) {
        return value;
    }

}
