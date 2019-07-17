package com.cherkashyn.vitalii.accounting.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PRICE")
public class Price {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = true)
	private Integer	id;

	@Column(name = "PRICE_BUY")
	private Float	priceBuy;

	@Column(name = "PRICE_SELL")
	private Float	priceSell;

	@Column(name = "ROWSTATUS")
	private short	rowstatus;

	@Column(name = "DESCRIPTION")
	private String	description;

	@Column(name = "CREATEDAT")
	private Date	createdAt;

	@OneToOne
	@JoinColumn(name = "NEXT_ID")
	private Price	next;

	public Price() {
		this(null, null, null);
	}

	public Price(Assortment assortment, Float priceBuy, Float priceSell) {
		this.priceBuy = priceBuy;
		this.priceSell = priceSell;
		this.rowstatus = RowStatus.ACTIVE.getValue();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getPriceBuy() {
		return priceBuy;
	}

	public void setPriceBuy(Float priceBuy) {
		this.priceBuy = priceBuy;
	}

	public Float getPriceSell() {
		return priceSell;
	}

	public void setPriceSell(Float priceSell) {
		this.priceSell = priceSell;
	}

	public short getRowstatus() {
		return rowstatus;
	}

	public void setRowstatus(short rowstatus) {
		this.rowstatus = rowstatus;
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

	public Price getNext() {
		return next;
	}

	public void setNext(Price next) {
		this.next = next;
	}

}
