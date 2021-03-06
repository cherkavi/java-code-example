package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * OcProductAttributeId generated by hbm2java
 */
@Embeddable
public class ProductAttributeId implements java.io.Serializable {

	private int productId;
	private int attributeId;
	private int languageId;

	public ProductAttributeId() {
	}

	public ProductAttributeId(int productId, int attributeId, int languageId) {
		this.productId = productId;
		this.attributeId = attributeId;
		this.languageId = languageId;
	}

	@Column(name = "product_id", nullable = false)
	public int getProductId() {
		return this.productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	@Column(name = "attribute_id", nullable = false)
	public int getAttributeId() {
		return this.attributeId;
	}

	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
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
		if (!(other instanceof ProductAttributeId))
			return false;
		ProductAttributeId castOther = (ProductAttributeId) other;

		return (this.getProductId() == castOther.getProductId())
				&& (this.getAttributeId() == castOther.getAttributeId())
				&& (this.getLanguageId() == castOther.getLanguageId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getProductId();
		result = 37 * result + this.getAttributeId();
		result = 37 * result + this.getLanguageId();
		return result;
	}

}
