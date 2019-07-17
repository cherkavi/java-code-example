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
@Table(name = "REDISCOUNT")
public class Rediscount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = true)
	private Integer		id;

	@Column(name = "IS_SOURCE", columnDefinition = "TINYINT")
	private boolean		source;

	@ManyToOne
	@JoinColumn(name = "ID_POINT")
	private Point		point;

	@Column(name = "DATE_REDISCOUNT")
	private Date		dateRediscount;

	@ManyToOne
	@JoinColumn(name = "ID_ASSORTMENT")
	private Assortment	assortment;

	@ManyToOne
	@JoinColumn(name = "ID_SERIAL")
	private Serial		serial;

	@Column(name = "CREATEDAT")
	private Date		createdAt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isSource() {
		return source;
	}

	public void setSource(boolean source) {
		this.source = source;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public Date getDateRediscount() {
		return dateRediscount;
	}

	public void setDateRediscount(Date dateRediscount) {
		this.dateRediscount = dateRediscount;
	}

	public Assortment getAssortment() {
		return assortment;
	}

	public void setAssortment(Assortment assortment) {
		this.assortment = assortment;
	}

	public Serial getSerial() {
		return serial;
	}

	public void setSerial(Serial serial) {
		this.serial = serial;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

}
