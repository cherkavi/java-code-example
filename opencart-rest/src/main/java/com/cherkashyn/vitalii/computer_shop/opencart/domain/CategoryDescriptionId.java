package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * OcCategoryDescriptionId generated by hbm2java
 */
@Embeddable
public class CategoryDescriptionId implements java.io.Serializable {

	private int categoryId;
	private int languageId;

	public CategoryDescriptionId() {
	}

	public CategoryDescriptionId(int categoryId, int languageId) {
		this.categoryId = categoryId;
		this.languageId = languageId;
	}

	@Column(name = "category_id", nullable = false)
	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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
		if (!(other instanceof CategoryDescriptionId))
			return false;
		CategoryDescriptionId castOther = (CategoryDescriptionId) other;

		return (this.getCategoryId() == castOther.getCategoryId())
				&& (this.getLanguageId() == castOther.getLanguageId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCategoryId();
		result = 37 * result + this.getLanguageId();
		return result;
	}

}
