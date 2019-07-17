package com.cherkashyn.vitalii.accounting.domain;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CURRENCY")
public class Currency {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = true)
    private Integer id;
    
    @Column(name="DOLLAR")
    private Float dollar;
    
    @Column(name="ROWSTATUS")
    private short rowstatus=RowStatus.ACTIVE.getValue();

    @Column(name = "CREATEDAT")
    private Date createdat=new Date();

    public Currency(){
    	this(null);
    }
    
    public Currency(Float dollar){
    	this.dollar=dollar;
    }
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreatedat() {
		return createdat;
	}

	public Float getDollar() {
		return dollar;
	}

	public void setDollar(Float dollar) {
		this.dollar = dollar;
	}

	public short getRowstatus() {
		return rowstatus;
	}

	public void setRowstatus(short rowstatus) {
		this.rowstatus = rowstatus;
	}
    
}
