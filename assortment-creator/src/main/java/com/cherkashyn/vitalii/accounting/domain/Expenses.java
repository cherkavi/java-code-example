package com.cherkashyn.vitalii.accounting.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EXPENSES")
public class Expenses {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = true)
    private Integer id;
    
    @Column(name="NAME")
    private String name;
    
    @Column(name="SIGN")
    private int sign;
    
    @Column(name="SIGN_SELLER")
    private int signSeller;
    
    @Column(name="DESCRIPTION")
    private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSign() {
		return sign;
	}

	public void setSign(int sign) {
		this.sign = sign;
	}

	public int getSignSeller() {
		return signSeller;
	}

	public void setSignSeller(int signSeller) {
		this.signSeller = signSeller;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
}
