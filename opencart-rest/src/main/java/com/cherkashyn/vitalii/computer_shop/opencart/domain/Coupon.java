package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * OcCoupon generated by hbm2java
 */
@Entity
@Table(name = "oc_coupon", catalog = "opencart")
public class Coupon implements java.io.Serializable {

	private Integer couponId;
	private String name;
	private String code;
	private char type;
	private BigDecimal discount;
	private boolean logged;
	private boolean shipping;
	private BigDecimal total;
	private Date dateStart;
	private Date dateEnd;
	private int usesTotal;
	private String usesCustomer;
	private boolean status;
	private Date dateAdded;

	public Coupon() {
	}

	public Coupon(String name, String code, char type, BigDecimal discount,
			boolean logged, boolean shipping, BigDecimal total, Date dateStart,
			Date dateEnd, int usesTotal, String usesCustomer, boolean status,
			Date dateAdded) {
		this.name = name;
		this.code = code;
		this.type = type;
		this.discount = discount;
		this.logged = logged;
		this.shipping = shipping;
		this.total = total;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.usesTotal = usesTotal;
		this.usesCustomer = usesCustomer;
		this.status = status;
		this.dateAdded = dateAdded;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "coupon_id", unique = true, nullable = false)
	public Integer getCouponId() {
		return this.couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}

	@Column(name = "name", nullable = false, length = 128)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "code", nullable = false, length = 10)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "type", nullable = false, length = 1)
	public char getType() {
		return this.type;
	}

	public void setType(char type) {
		this.type = type;
	}

	@Column(name = "discount", nullable = false, precision = 15, scale = 4)
	public BigDecimal getDiscount() {
		return this.discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	@Column(name = "logged", nullable = false)
	public boolean isLogged() {
		return this.logged;
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}

	@Column(name = "shipping", nullable = false)
	public boolean isShipping() {
		return this.shipping;
	}

	public void setShipping(boolean shipping) {
		this.shipping = shipping;
	}

	@Column(name = "total", nullable = false, precision = 15, scale = 4)
	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_start", nullable = false, length = 10)
	public Date getDateStart() {
		return this.dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_end", nullable = false, length = 10)
	public Date getDateEnd() {
		return this.dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	@Column(name = "uses_total", nullable = false)
	public int getUsesTotal() {
		return this.usesTotal;
	}

	public void setUsesTotal(int usesTotal) {
		this.usesTotal = usesTotal;
	}

	@Column(name = "uses_customer", nullable = false, length = 11)
	public String getUsesCustomer() {
		return this.usesCustomer;
	}

	public void setUsesCustomer(String usesCustomer) {
		this.usesCustomer = usesCustomer;
	}

	@Column(name = "status", nullable = false)
	public boolean isStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_added", nullable = false, length = 19)
	public Date getDateAdded() {
		return this.dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

}
