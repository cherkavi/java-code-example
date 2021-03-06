package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * OcCustomFieldValueDescriptionId generated by hbm2java
 */
@Embeddable
public class CustomFieldValueDescriptionId implements java.io.Serializable {

	private int customFieldValueId;
	private int languageId;

	public CustomFieldValueDescriptionId() {
	}

	public CustomFieldValueDescriptionId(int customFieldValueId,
			int languageId) {
		this.customFieldValueId = customFieldValueId;
		this.languageId = languageId;
	}

	@Column(name = "custom_field_value_id", nullable = false)
	public int getCustomFieldValueId() {
		return this.customFieldValueId;
	}

	public void setCustomFieldValueId(int customFieldValueId) {
		this.customFieldValueId = customFieldValueId;
	}

	@Column(name = "language_id", nullable = false)
	public int getLanguageId() {
		return this.languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CustomFieldValueDescriptionId))
			return false;
		CustomFieldValueDescriptionId castOther = (CustomFieldValueDescriptionId) other;

		return (this.getCustomFieldValueId() == castOther
				.getCustomFieldValueId())
				&& (this.getLanguageId() == castOther.getLanguageId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCustomFieldValueId();
		result = 37 * result + this.getLanguageId();
		return result;
	}

}
