package com.domain.${nameLowCase};

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TABLE_NAME")
public class ${nameReal}
    extends EntitySupport {

    @Column(name = "NAME")
    private String name;

    public ${nameReal}() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
