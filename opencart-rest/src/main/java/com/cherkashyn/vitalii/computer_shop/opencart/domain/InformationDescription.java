package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * OcInformationDescription generated by hbm2java
 */
@Entity
@Table(name = "oc_information_description", catalog = "opencart")
public class InformationDescription implements java.io.Serializable {

	private InformationDescriptionId id;
	private String title;
	private String description;

	public InformationDescription() {
	}

	public InformationDescription(InformationDescriptionId id,
			String title, String description) {
		this.id = id;
		this.title = title;
		this.description = description;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "informationId", column = @Column(name = "information_id", nullable = false)),
			@AttributeOverride(name = "languageId", column = @Column(name = "language_id", nullable = false)) })
	public InformationDescriptionId getId() {
		return this.id;
	}

	public void setId(InformationDescriptionId id) {
		this.id = id;
	}

	@Column(name = "title", nullable = false, length = 64)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "description", nullable = false, length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
