package com.cherkashyn.vitalii.step;

import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StepContext {

    private final Map<String, Object> parameters;
    private List<Class<? extends Step>> steps;
    private ApplicationContext context;

    public StepContext(Map<String, Object> parameters){
        this.parameters = new HashMap<>(parameters);
        this.steps = new LinkedList<>();
    }

    public StepContext setContext(ApplicationContext context){
        this.context = context;
        return this;
    }

    public StepContext step(Class<? extends Step> nextStep){
        this.steps.add(nextStep);
        return this;
    }

    public static StepContext create(Map.Entry<String, Object> ... entries){
        Map<String, Object> parameters = new HashMap<>();
        if(entries!=null && entries.length>0){
            for(Map.Entry<String, Object> entry : entries){
                parameters.put(entry.getKey(), entry.getValue());
            }
        }
        return new StepContext(parameters);
    }

    @FunctionalInterface
    public interface Result<T>{
        T obtain();
    }

    public <T> T execute(Result<T> result) {
        this.steps.forEach(step->this.context.getBean(step).execute(this.parameters));
        return result.obtain();
    }
}
