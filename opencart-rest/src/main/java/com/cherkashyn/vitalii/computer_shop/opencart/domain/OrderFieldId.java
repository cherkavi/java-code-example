package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * OcOrderFieldId generated by hbm2java
 */
@Embeddable
public class OrderFieldId implements java.io.Serializable {

	private int orderId;
	private int customFieldId;
	private int customFieldValueId;

	public OrderFieldId() {
	}

	public OrderFieldId(int orderId, int customFieldId, int customFieldValueId) {
		this.orderId = orderId;
		this.customFieldId = customFieldId;
		this.customFieldValueId = customFieldValueId;
	}

	@Column(name = "order_id", nullable = false)
	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	@Column(name = "custom_field_id", nullable = false)
	public int getCustomFieldId() {
		return this.customFieldId;
	}

	public void setCustomFieldId(int customFieldId) {
		this.customFieldId = customFieldId;
	}

	@Column(name = "custom_field_value_id", nullable = false)
	public int getCustomFieldValueId() {
		return this.customFieldValueId;
	}

	public void setCustomFieldValueId(int customFieldValueId) {
		this.customFieldValueId = customFieldValueId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof OrderFieldId))
			return false;
		OrderFieldId castOther = (OrderFieldId) other;

		return (this.getOrderId() == castOther.getOrderId())
				&& (this.getCustomFieldId() == castOther.getCustomFieldId())
				&& (this.getCustomFieldValueId() == castOther
						.getCustomFieldValueId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getOrderId();
		result = 37 * result + this.getCustomFieldId();
		result = 37 * result + this.getCustomFieldValueId();
		return result;
	}

}
