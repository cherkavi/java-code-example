package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * OcLengthClassDescription generated by hbm2java
 */
@Entity
@Table(name = "oc_length_class_description", catalog = "opencart")
public class LengthClassDescription implements java.io.Serializable {

	private LengthClassDescriptionId id;
	private String title;
	private String unit;

	public LengthClassDescription() {
	}

	public LengthClassDescription(LengthClassDescriptionId id,
			String title, String unit) {
		this.id = id;
		this.title = title;
		this.unit = unit;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "lengthClassId", column = @Column(name = "length_class_id", nullable = false)),
			@AttributeOverride(name = "languageId", column = @Column(name = "language_id", nullable = false)) })
	public LengthClassDescriptionId getId() {
		return this.id;
	}

	public void setId(LengthClassDescriptionId id) {
		this.id = id;
	}

	@Column(name = "title", nullable = false, length = 32)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "unit", nullable = false, length = 4)
	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
