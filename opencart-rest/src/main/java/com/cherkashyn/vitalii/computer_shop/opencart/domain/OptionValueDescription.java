package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * OcOptionValueDescription generated by hbm2java
 */
@Entity
@Table(name = "oc_option_value_description", catalog = "opencart")
public class OptionValueDescription implements java.io.Serializable {

	private OptionValueDescriptionId id;
	private int optionId;
	private String name;

	public OptionValueDescription() {
	}

	public OptionValueDescription(OptionValueDescriptionId id,
			int optionId, String name) {
		this.id = id;
		this.optionId = optionId;
		this.name = name;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "optionValueId", column = @Column(name = "option_value_id", nullable = false)),
			@AttributeOverride(name = "languageId", column = @Column(name = "language_id", nullable = false)) })
	public OptionValueDescriptionId getId() {
		return this.id;
	}

	public void setId(OptionValueDescriptionId id) {
		this.id = id;
	}

	@Column(name = "option_id", nullable = false)
	public int getOptionId() {
		return this.optionId;
	}

	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}

	@Column(name = "name", nullable = false, length = 128)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
