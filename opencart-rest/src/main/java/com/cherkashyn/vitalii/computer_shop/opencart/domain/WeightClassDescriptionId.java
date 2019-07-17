package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * OcWeightClassDescriptionId generated by hbm2java
 */
@Embeddable
public class WeightClassDescriptionId implements java.io.Serializable {

	private int weightClassId;
	private int languageId;

	public WeightClassDescriptionId() {
	}

	public WeightClassDescriptionId(int weightClassId, int languageId) {
		this.weightClassId = weightClassId;
		this.languageId = languageId;
	}

	@Column(name = "weight_class_id", nullable = false)
	public int getWeightClassId() {
		return this.weightClassId;
	}

	public void setWeightClassId(int weightClassId) {
		this.weightClassId = weightClassId;
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
		if (!(other instanceof WeightClassDescriptionId))
			return false;
		WeightClassDescriptionId castOther = (WeightClassDescriptionId) other;

		return (this.getWeightClassId() == castOther.getWeightClassId())
				&& (this.getLanguageId() == castOther.getLanguageId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getWeightClassId();
		result = 37 * result + this.getLanguageId();
		return result;
	}

}
