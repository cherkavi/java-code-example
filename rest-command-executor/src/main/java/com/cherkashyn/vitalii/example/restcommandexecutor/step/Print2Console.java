package com.cherkashyn.vitalii.example.restcommandexecutor.step;

import com.cherkashyn.vitalii.step.Step;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Print2Console implements Step{

    @Override
    public void execute(Map<String, Object> parameters) {
        System.out.println(" >>> "+parameters.get("key")+" <<< ");
    }

}
