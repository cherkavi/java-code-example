package com.cherkashyn.vitalii.examples.springshell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class PrintBeans {

    @Autowired
    ApplicationContext context;

    @ShellMethod("get bean by name ")
    public Object beanByName(@ShellOption(defaultValue = "exampleBean") String name){
        return context.getBean(name);
    }
}
