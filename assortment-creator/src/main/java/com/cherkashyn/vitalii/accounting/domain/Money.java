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
@Table(name = "MONEY")
public class Money {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = true)
	private Integer		id;

	@ManyToOne
	@JoinColumn(name = "ID_POINT")
	private Point		point;

	@ManyToOne
	@JoinColumn(name = "ID_EXPENSES")
	private Expenses	expenses;

	@ManyToOne
	@JoinColumn(name = "ID_PEOPLE")
	private People		people;

	@Column(name = "AMOUNT")
	private float		amount;

	@Column(name = "DATE_IN_OUT")
	private Date		dateInOut;

	@Column(name = "CREATEDAT")
	private Date		createdAt;

	@Column(name = "DESCRIPTION")
	private String		description;

	@ManyToOne
	@JoinColumn(name = "ID_COMMODITY")
	private Commodity	commodity;

	@ManyToOne
	@JoinColumn(name = "ID_CLIENT")
	private Client		client;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public Expenses getExpenses() {
		return expenses;
	}

	public void setExpenses(Expenses expenses) {
		this.expenses = expenses;
	}

	public People getPeople() {
		return people;
	}

	public void setPeople(People people) {
		this.people = people;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public Date getDateInOut() {
		return dateInOut;
	}

	public void setDateInOut(Date dateInOut) {
		this.dateInOut = dateInOut;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Commodity getCommodity() {
		return commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
