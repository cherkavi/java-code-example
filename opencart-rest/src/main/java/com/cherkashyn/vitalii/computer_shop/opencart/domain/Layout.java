package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OcLayout generated by hbm2java
 */
@Entity
@Table(name = "oc_layout", catalog = "opencart")
public class Layout implements java.io.Serializable {

	private Integer layoutId;
	private String name;

	public Layout() {
	}

	public Layout(String name) {
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "layout_id", unique = true, nullable = false)
	public Integer getLayoutId() {
		return this.layoutId;
	}

	public void setLayoutId(Integer layoutId) {
		this.layoutId = layoutId;
	}

	@Column(name = "name", nullable = false, length = 64)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
