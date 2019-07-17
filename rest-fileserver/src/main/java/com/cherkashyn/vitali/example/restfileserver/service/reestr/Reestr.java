package com.cherkashyn.vitali.example.restfileserver.service.reestr;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.util.Date;
import java.util.UUID;

@Entity
public class Reestr {

    public static enum Status{
        NEW,
        DEPLOYING,
        RUNNING,
        REMOVING,
        REMOVED
    }

    @Id
    private String id;

    private String originalFileName;

    private String branchName;

    private String status;

    private Date statusDate;

    @PrePersist
    public void prePersist(){
        this.statusDate = new Date();
        if(this.id==null){
            this.id = UUID.randomUUID().toString();
        }
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
