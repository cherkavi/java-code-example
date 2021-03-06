package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * OcFilterGroupDescription generated by hbm2java
 */
@Entity
@Table(name = "oc_filter_group_description", catalog = "opencart")
public class FilterGroupDescription implements java.io.Serializable {

	private FilterGroupDescriptionId id;
	private String name;

	public FilterGroupDescription() {
	}

	public FilterGroupDescription(FilterGroupDescriptionId id, String name) {
		this.id = id;
		this.name = name;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "filterGroupId", column = @Column(name = "filter_group_id", nullable = false)),
			@AttributeOverride(name = "languageId", column = @Column(name = "language_id", nullable = false)) })
	public FilterGroupDescriptionId getId() {
		return this.id;
	}

	public void setId(FilterGroupDescriptionId id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 64)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
