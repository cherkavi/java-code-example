package com.cherkashyn.vitalii.tools.usermanager.console;

import com.cherkashyn.vitalii.tools.usermanager.domain.Process;
import com.cherkashyn.vitalii.tools.usermanager.jpa.ProcessRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Arrays;
import java.util.Optional;

@ShellComponent
@ShellCommandGroup("process")
public class ProcessManagement {
    private final static String RESULT_OK="ok";

    @Autowired
    ProcessRepository processRepository;

    @ShellMethod("list of processes")
    public Iterable<Process> processList(){
        return processRepository.findAll();
    }

    @ShellMethod("process by id")
    public Iterable<Process> processById(@ShellOption Integer id){
        return getUserIterable(processRepository.findById(id));
    }

    @ShellMethod("process by name")
    public Iterable<Process> processByName(@ShellOption String name){
        return getUserIterable(processRepository.findByName(name));
    }

    private Iterable<Process> getUserIterable(Optional<Process> process) {
        if(! (process).isPresent() ){
            return null;
        }else{
            return Arrays.asList(process.get());
        }
    }

    private String prepareString(String value){
        return StringUtils.lowerCase(StringUtils.trimToNull(value));
    }

    @ShellMethod("add process")
    public Process processAdd(@ShellOption String name){
        if(this.processRepository.findByName(prepareString(name)).isPresent()){
            return null;
        }
        Process user = new Process();
        user.setName(prepareString(name));
        return this.processRepository.save(user);
    }

    @ShellMethod("update process ")
    public String processUpdate(@ShellOption int processId, @ShellOption String name){
        Optional<Process> process = this.processRepository.findById(processId);
        if(!process.isPresent()){
            return "ERROR: process with id: "+processId+" was not found";
        }
        process.get().setName(prepareString(name));
        this.processRepository.save(process.get());
        return RESULT_OK;
    }

}
