package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * OcOrderStatus generated by hbm2java
 */
@Entity
@Table(name = "oc_order_status", catalog = "opencart")
public class OrderStatus implements java.io.Serializable {

	private OrderStatusId id;
	private String name;

	public OrderStatus() {
	}

	public OrderStatus(OrderStatusId id, String name) {
		this.id = id;
		this.name = name;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "orderStatusId", column = @Column(name = "order_status_id", nullable = false)),
			@AttributeOverride(name = "languageId", column = @Column(name = "language_id", nullable = false)) })
	public OrderStatusId getId() {
		return this.id;
	}

	public void setId(OrderStatusId id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 32)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
