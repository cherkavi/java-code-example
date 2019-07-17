package com.cherkashyn.vitalii.example.restjdbcstatus.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
public class DeployStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotBlank
    private String key;

    private Date createDate;


    @PrePersist
    public void prePersist(){
        if(Objects.isNull(createDate)){
            createDate=new Date();
        }
    }

    public DeployStatus(){
    }

    public DeployStatus(@NotNull Status status, @NotBlank String key) {
        this.status = status;
        this.key = key;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getCreateDate() {
        return createDate;
    }

}

