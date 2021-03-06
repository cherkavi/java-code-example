package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OcOption generated by hbm2java
 */
@Entity
@Table(name = "oc_option", catalog = "opencart")
public class Option implements java.io.Serializable {

	private Integer optionId;
	private String type;
	private int sortOrder;

	public Option() {
	}

	public Option(String type, int sortOrder) {
		this.type = type;
		this.sortOrder = sortOrder;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "option_id", unique = true, nullable = false)
	public Integer getOptionId() {
		return this.optionId;
	}

	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}

	@Column(name = "type", nullable = false, length = 32)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "sort_order", nullable = false)
	public int getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

}
