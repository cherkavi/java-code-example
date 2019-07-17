package com.cherkashyn.vitalii.tools.usermanager.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="users_process")
// SequenceGenerator(name = "seq_users",  sequenceName = "seq_users", allocationSize = 1)
public class Process {
    @Id
    // GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_users")
    private int id;

    @Column(unique = true)
    private String name;

    @Column
    private Date createdDate;

    @PrePersist
    protected void onCreate() {
        createdDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        createdDate = new Date();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public String toString() {
        return "Process{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
