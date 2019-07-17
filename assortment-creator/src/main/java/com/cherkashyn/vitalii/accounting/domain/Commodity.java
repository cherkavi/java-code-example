package com.cherkashyn.vitalii.accounting.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "COMMODITY")
public class Commodity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = true)
	private Integer		id;

	@ManyToOne
	@JoinColumn(name = "ID_ASSORTMENT")
	private Assortment	assortment;

	@Column(name = "QUANTITY")
	private Integer		quantity;

	@ManyToOne
	@JoinColumn(name = "ID_OPERATION")
	private Operation	operation;

	@Column(name = "DATE_IN_OUT")
	private Date		dateInOut;

	@Column(name = "DESCRIPTION")
	private String		description;

	@Column(name = "CREATEDAT")
	private Date		createdAt;

	@ManyToOne
	@JoinColumn(name = "ID_SERIAL")
	private Serial		serial;

	@Column(name = "DISCOUNT")
	private Float		discount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Assortment getAssortment() {
		return assortment;
	}

	public void setAssortment(Assortment assortment) {
		this.assortment = assortment;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public Date getDateInOut() {
		return dateInOut;
	}

	public void setDateInOut(Date dateInOut) {
		this.dateInOut = dateInOut;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Serial getSerial() {
		return serial;
	}

	public void setSerial(Serial serial) {
		this.serial = serial;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

}
