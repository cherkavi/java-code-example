package com.cherkashyn.vitalii.example.restcommandexecutor.controller;

import com.cherkashyn.vitalii.example.restcommandexecutor.step.Print2Console;
import com.cherkashyn.vitalii.step.StepContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.AbstractMap;

@Controller
public class StepExecutor {

    @Autowired
    ApplicationContext context;

    // curl -X POST -F "key=234" http://localhost:8080/proceed
    @PostMapping(path="/proceed")
    @ResponseBody
    public String stepExecutor(@RequestParam("key") String key){
        return StepContext
                .create(new AbstractMap.SimpleImmutableEntry<>("key", key))
                .setContext(context)
                .step(Print2Console.class)
                .step(Print2Console.class)
                .execute(()-> "OK");
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> exceptionHandling(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

}
