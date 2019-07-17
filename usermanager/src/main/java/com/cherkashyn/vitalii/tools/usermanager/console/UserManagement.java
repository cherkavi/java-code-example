package com.cherkashyn.vitalii.tools.usermanager.console;

import com.cherkashyn.vitalii.tools.usermanager.domain.Group;
import com.cherkashyn.vitalii.tools.usermanager.domain.Process;
import com.cherkashyn.vitalii.tools.usermanager.domain.User;
import com.cherkashyn.vitalii.tools.usermanager.jpa.GroupRepository;
import com.cherkashyn.vitalii.tools.usermanager.jpa.ProcessRepository;
import com.cherkashyn.vitalii.tools.usermanager.jpa.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.StreamUtils;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import sshd.shell.springboot.autoconfiguration.SshdShellCommand;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ShellComponent
@ShellCommandGroup("user")
@SshdShellCommand(value="user", description = "operations with user")
public class UserManagement {
    private final static String RESULT_OK="ok";

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    ProcessRepository processRepository;

    @ShellMethod("list of all users")
    public Iterable<User> userList(){
        return userRepository.findAll();
    }

    // user list
    @SshdShellCommand(value="list", description = "operations with user")
    public String remoteUserList(String args){
        return StreamUtils.createStreamFromIterator(userRepository.findAll().iterator()).map(User::toString).collect(Collectors.joining("\n"));
    }

    @ShellMethod("list of users by group ")
    public Iterable<User> userByGroup(@ShellOption String group){
        Optional<Group> savedGroup = this.groupRepository.findByName(group);
        if(!savedGroup.isPresent()){
            return null;
        }
        return userRepository.getByGroups(savedGroup);
    }

    @ShellMethod("user by id")
    public Iterable<User> userById(@ShellOption Integer id){
        return getUserIterable(userRepository.findById(id));
    }

    @ShellMethod("user by id")
    public String remoteUserById(String args){
        Optional<User> user = userRepository.findById(Integer.parseInt(args));
        if(!user.isPresent()){
            return StringUtils.EMPTY;
        }else{
            return user.get().toString();
        }
    }

    @ShellMethod("user by Name")
    public Iterable<User> userByName(@ShellOption String name){
        return getUserIterable(userRepository.findByName(name));
    }

    @ShellMethod("user by Surname")
    public Iterable<User> userBySurname(@ShellOption String surname){
        return getUserIterable(userRepository.findBySurname(surname));
    }

    @ShellMethod("user by Login")
    public Iterable<User> userByLogin(@ShellOption String login){
        return getUserIterable(userRepository.findByLogin(login));
    }

    private Iterable<User> getUserIterable(Optional<User> user) {
        if(! (user).isPresent() ){
            return null;
        }else{
            return Arrays.asList(user.get());
        }
    }

    @ShellMethod("add user")
    public User userAdd(@ShellOption String login, @ShellOption String password, @ShellOption(defaultValue = ShellOption.NULL) String name, @ShellOption(defaultValue = ShellOption.NULL) String surname){
        User user = new User();
        user.setName(prepareString(name));
        user.setSurname(prepareString(surname));
        user.setLogin(prepareString(login));
        user.setPassword(prepareString(password));
        return this.userRepository.save(user);
    }

    @ShellMethod("update user ")
    public String userUpdate(@ShellOption int userId, @ShellOption(defaultValue = ShellOption.NULL) String name, @ShellOption(defaultValue = ShellOption.NULL) String surname, @ShellOption(defaultValue = ShellOption.NULL) String login, @ShellOption(defaultValue = ShellOption.NULL) String password){
        Optional<User> user = this.userRepository.findById(userId);
        if(!user.isPresent()){
            return "ERROR: user with id: "+userId+" was not found";
        }
        if(name!=null){
            user.get().setName(prepareString(name));
        }
        if(surname!=null){
            user.get().setSurname(prepareString(surname));
        }
        if(login!=null){
            user.get().setLogin(prepareString(login));
        }
        if(password!=null){
            user.get().setPassword(prepareString(login));
        }
        this.userRepository.save(user.get());
        return RESULT_OK;
    }

    private String prepareString(String value){
        return StringUtils.lowerCase(StringUtils.trimToNull(value));
    }

    @ShellMethod("add group to user, assign group to user")
    public String userAddGroup(@ShellOption String userLogin, @ShellOption String groupName){
        Optional<Group> group = this.groupRepository.findByName(prepareString(groupName));
        if(!group.isPresent()){
            return "group: "+groupName+" was not found";
        }
        Optional<User> user = this.userRepository.findByLogin(userLogin);
        if(!user.isPresent()){
            return "login: "+userLogin+" was not found";
        }

        if(userContainsGroup(user.get(), group.get())){
            return String.format(" group [%s] already assigned to user [%s]", groupName, userLogin);
        }
        user.get().getGroups().add(group.get());
        userRepository.save(user.get());
        return RESULT_OK;
    }

    @ShellMethod("add process to user, assign process to user")
    public String userAddProcess(@ShellOption String userLogin, @ShellOption String processName){
        Optional<Process> process = this.processRepository.findByName(prepareString(processName));
        if(!process.isPresent()){
            return "process: "+processName+" was not found";
        }
        Optional<User> user = this.userRepository.findByLogin(userLogin);
        if(!user.isPresent()){
            return "login: "+userLogin+" was not found";
        }

        if(userContainsProcess(user.get(), process.get())){
            return String.format(" process [%s] already assigned to user [%s]", processName, userLogin);
        }
        user.get().getProcesses().add(process.get());
        userRepository.save(user.get());
        return RESULT_OK;
    }

    @ShellMethod("remove group from user, unassign group from user")
    public String userRemoveGroup(@ShellOption String userLogin, @ShellOption String groupName){
        Optional<Group> group = this.groupRepository.findByName(prepareString(groupName));
        if(!group.isPresent()){
            return "group: "+groupName+" was not found";
        }
        Optional<User> user = this.userRepository.findByLogin(userLogin);
        if(!user.isPresent()){
            return "login: "+userLogin+" was not found";
        }
        if(!userContainsGroup(user.get(), group.get())){
            return String.format(" group [%s] is not assigned to user [%s]", groupName, userLogin);
        }
        removeGroupByName(user.get().getGroups(), group.get().getName());
        userRepository.save(user.get());
        return RESULT_OK;
    }

    @ShellMethod("remove process from user, unassign process from user")
    public String userRemoveProcess(@ShellOption String userLogin, @ShellOption String processName){
        Optional<Process> process = this.processRepository.findByName(prepareString(processName));
        if(!process.isPresent()){
            return "process: "+processName+" was not found";
        }
        Optional<User> user = this.userRepository.findByLogin(userLogin);
        if(!user.isPresent()){
            return "login: "+userLogin+" was not found";
        }
        if(!userContainsProcess(user.get(), process.get())){
            return String.format(" process [%s] is not assigned to user [%s]", processName, userLogin);
        }
        removeProcessByName(user.get().getProcesses(), process.get().getName());
        userRepository.save(user.get());
        return RESULT_OK;
    }

    private void removeGroupByName(Set<Group> groups, String name) {
        Iterator<Group> iterator = groups.iterator();
        while(iterator.hasNext()){
            Group group = iterator.next();
            if(group.getName().equals(name)){
                iterator.remove();
                return;
            }
        }
    }

    private void removeProcessByName(Set<Process> processes, String name) {
        Iterator<Process> iterator = processes.iterator();
        while(iterator.hasNext()){
            Process process = iterator.next();
            if(process.getName().equals(name)){
                iterator.remove();
                return;
            }
        }
    }

    private boolean userContainsGroup(User user, Group group) {
        return user.getGroups().stream().map(Group::getName).collect(Collectors.toList()).contains(group.getName());
    }

    private boolean userContainsProcess(User user, Process process) {
        return user.getProcesses().stream().map(Process::getName).collect(Collectors.toList()).contains(process.getName());
    }


}
