package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * OcFilterDescription generated by hbm2java
 */
@Entity
@Table(name = "oc_filter_description", catalog = "opencart")
public class FilterDescription implements java.io.Serializable {

	private FilterDescriptionId id;
	private int filterGroupId;
	private String name;

	public FilterDescription() {
	}

	public FilterDescription(FilterDescriptionId id, int filterGroupId,
			String name) {
		this.id = id;
		this.filterGroupId = filterGroupId;
		this.name = name;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "filterId", column = @Column(name = "filter_id", nullable = false)),
			@AttributeOverride(name = "languageId", column = @Column(name = "language_id", nullable = false)) })
	public FilterDescriptionId getId() {
		return this.id;
	}

	public void setId(FilterDescriptionId id) {
		this.id = id;
	}

	@Column(name = "filter_group_id", nullable = false)
	public int getFilterGroupId() {
		return this.filterGroupId;
	}

	public void setFilterGroupId(int filterGroupId) {
		this.filterGroupId = filterGroupId;
	}

	@Column(name = "name", nullable = false, length = 64)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
