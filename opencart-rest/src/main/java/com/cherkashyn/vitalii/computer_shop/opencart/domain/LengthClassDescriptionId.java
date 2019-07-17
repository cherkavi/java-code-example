package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * OcLengthClassDescriptionId generated by hbm2java
 */
@Embeddable
public class LengthClassDescriptionId implements java.io.Serializable {

	private int lengthClassId;
	private int languageId;

	public LengthClassDescriptionId() {
	}

	public LengthClassDescriptionId(int lengthClassId, int languageId) {
		this.lengthClassId = lengthClassId;
		this.languageId = languageId;
	}

	@Column(name = "length_class_id", nullable = false)
	public int getLengthClassId() {
		return this.lengthClassId;
	}

	public void setLengthClassId(int lengthClassId) {
		this.lengthClassId = lengthClassId;
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
		if (!(other instanceof LengthClassDescriptionId))
			return false;
		LengthClassDescriptionId castOther = (LengthClassDescriptionId) other;

		return (this.getLengthClassId() == castOther.getLengthClassId())
				&& (this.getLanguageId() == castOther.getLanguageId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getLengthClassId();
		result = 37 * result + this.getLanguageId();
		return result;
	}

}
