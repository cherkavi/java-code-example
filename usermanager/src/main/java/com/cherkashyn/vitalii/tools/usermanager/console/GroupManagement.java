package com.cherkashyn.vitalii.tools.usermanager.console;

import com.cherkashyn.vitalii.tools.usermanager.domain.Group;
import com.cherkashyn.vitalii.tools.usermanager.jpa.GroupRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Arrays;
import java.util.Optional;

@ShellComponent
@ShellCommandGroup("group")
public class GroupManagement {
    private final static String RESULT_OK="ok";

    @Autowired
    GroupRepository groupRepository;

    @ShellMethod("list of groups")
    public Iterable<Group> groupList(){
        return groupRepository.findAll();
    }

    @ShellMethod("group by id")
    public Iterable<Group> groupById(@ShellOption Integer id){
        return getUserIterable(groupRepository.findById(id));
    }

    @ShellMethod("group by name")
    public Iterable<Group> groupByName(@ShellOption String name){
        return getUserIterable(groupRepository.findByName(name));
    }

    private Iterable<Group> getUserIterable(Optional<Group> group) {
        if(! (group).isPresent() ){
            return null;
        }else{
            return Arrays.asList(group.get());
        }
    }

    private String prepareString(String value){
        return StringUtils.lowerCase(StringUtils.trimToNull(value));
    }

    @ShellMethod("add group")
    public Group groupAdd(@ShellOption String name){
        if(this.groupRepository.findByName(prepareString(name)).isPresent()){
            return null;
        }
        Group user = new Group();
        user.setName(prepareString(name));
        return this.groupRepository.save(user);
    }

    @ShellMethod("update group ")
    public String groupUpdate(@ShellOption int groupId, @ShellOption String name){
        Optional<Group> group = this.groupRepository.findById(groupId);
        if(!group.isPresent()){
            return "ERROR: group with id: "+groupId+" was not found";
        }
        group.get().setName(prepareString(name));
        this.groupRepository.save(group.get());
        return RESULT_OK;
    }

}
