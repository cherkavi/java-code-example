package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * OcFilterDescriptionId generated by hbm2java
 */
@Embeddable
public class FilterDescriptionId implements java.io.Serializable {

	private int filterId;
	private int languageId;

	public FilterDescriptionId() {
	}

	public FilterDescriptionId(int filterId, int languageId) {
		this.filterId = filterId;
		this.languageId = languageId;
	}

	@Column(name = "filter_id", nullable = false)
	public int getFilterId() {
		return this.filterId;
	}

	public void setFilterId(int filterId) {
		this.filterId = filterId;
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
		if (!(other instanceof FilterDescriptionId))
			return false;
		FilterDescriptionId castOther = (FilterDescriptionId) other;

		return (this.getFilterId() == castOther.getFilterId())
				&& (this.getLanguageId() == castOther.getLanguageId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getFilterId();
		result = 37 * result + this.getLanguageId();
		return result;
	}

}
