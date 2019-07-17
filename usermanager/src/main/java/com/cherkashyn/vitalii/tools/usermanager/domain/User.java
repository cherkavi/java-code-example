package com.cherkashyn.vitalii.tools.usermanager.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="users_user")
// SequenceGenerator(name = "seq_users",  sequenceName = "seq_users", allocationSize = 1)
public class User {
    @Id
    // GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_users")
    private int id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column(unique = true)
    private String login;

    @Column
    private String password;

    @Column
    @OneToMany(targetEntity = Group.class, fetch = FetchType.EAGER)
    private Set<Group> groups;

    @Column
    @OneToMany(targetEntity = Process.class, fetch = FetchType.EAGER)
    private Set<Process> processes;

    @Column
    private Date createdDate;

    @PrePersist
    protected void onCreate() {
        createdDate = new Date();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(Set<Process> processes) {
        this.processes = processes;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", groups=" + groups +
                ", processes=" + processes +
                ", createdDate=" + createdDate +
                '}';
    }
}
