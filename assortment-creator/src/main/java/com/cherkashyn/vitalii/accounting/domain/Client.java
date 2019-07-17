package com.cherkashyn.vitalii.accounting.domain;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CLIENT")
public class Client {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = true)
    private Integer id;
    
    @Column(name="BAR_CODE")
    private String barCode;
    
    @Column(name="EMAIL")
    private String email;
    
    @Column(name="PHONE")
    private String phone;
    
    @Column(name="NAME")
    private String name;
    
    @Column(name="SURNAME")
    private String surname;
    
    @Column(name="FATHER_NAME")
    private String fatherName;
    
    @Column(name="DESCRIPTION")
    private String description;
    
    @Column(name="PERCENT")
    private float percent;
    
    @Column(name="CREATEDAT")
    private Date createdAt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPercent() {
		return percent;
	}

	public void setPercent(float percent) {
		this.percent = percent;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

}
