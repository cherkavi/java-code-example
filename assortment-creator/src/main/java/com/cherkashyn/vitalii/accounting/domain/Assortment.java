package com.cherkashyn.vitalii.accounting.domain;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="ASSORTMENT")
public class Assortment {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = true)
    private Integer id;

    @Column(name="NAME")
    private String name;

    @Column(name="DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name="ID_TYPE")
    private AssortmentType type;

    @OneToMany
    @JoinColumn(name="ID_PRICE")
    private Set<Price> price;

    @Column(name="BAR_CODE")
    private String barCode;

    @Column(name="BAR_CODE_COMPANY")
    private String barCodeCompany;
    
    @OneToOne
    @JoinColumn(name="ID_PHOTO")
    private Photo photo;
    
    @Column(name="CREATED_AT")
    private Date createdAt;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AssortmentType getType() {
		return type;
	}

	public void setType(AssortmentType type) {
		this.type = type;
	}

	public Set<Price> getPrice() {
		return price;
	}

	public void setPrice(Set<Price> price) {
		this.price = price;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getBarCodeCompany() {
		return barCodeCompany;
	}

	public void setBarCodeCompany(String barCodeCompany) {
		this.barCodeCompany = barCodeCompany;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

}
