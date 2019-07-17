package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * OcOrderField generated by hbm2java
 */
@Entity
@Table(name = "oc_order_field", catalog = "opencart")
public class OrderField implements java.io.Serializable {

	private OrderFieldId id;
	private int name;
	private String value;
	private int sortOrder;

	public OrderField() {
	}

	public OrderField(OrderFieldId id, int name, String value, int sortOrder) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.sortOrder = sortOrder;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "orderId", column = @Column(name = "order_id", nullable = false)),
			@AttributeOverride(name = "customFieldId", column = @Column(name = "custom_field_id", nullable = false)),
			@AttributeOverride(name = "customFieldValueId", column = @Column(name = "custom_field_value_id", nullable = false)) })
	public OrderFieldId getId() {
		return this.id;
	}

	public void setId(OrderFieldId id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false)
	public int getName() {
		return this.name;
	}

	public void setName(int name) {
		this.name = name;
	}

	@Column(name = "value", nullable = false, length = 65535)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "sort_order", nullable = false)
	public int getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

}
