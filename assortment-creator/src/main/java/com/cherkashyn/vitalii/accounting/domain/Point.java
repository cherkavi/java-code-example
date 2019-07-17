package com.cherkashyn.vitalii.accounting.domain;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="POINT")
public class Point {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = true)
    private Integer id;

    @Column(name="NAME")
    private String name;
    
    @Column(name="ADDRESS")
    private String address;
    
    @Column(name="DESCRIPTION")
    private String description;
    
    @Column(name="IS_TRADE", columnDefinition="TINYINT")
    private boolean trade;
    
    @Column(name="PHONE")
    private String phone;
    
    @Column(name="EMAIL")
    private String email;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="PEOPLE2POINT", 
				joinColumns={@JoinColumn(name="ID_POINT", nullable=false)},
    			inverseJoinColumns={@JoinColumn(name="ID_PEOPLE", nullable=false)} 
    			)
    private Set<People> people;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isTrade() {
		return trade;
	}

	public void setTrade(boolean trade) {
		this.trade = trade;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
}
