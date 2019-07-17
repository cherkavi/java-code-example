package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * OcProductToCategoryId generated by hbm2java
 */
@Embeddable
public class ProductToCategoryId implements java.io.Serializable {

	private int productId;
	private int categoryId;

	public ProductToCategoryId() {
	}

	public ProductToCategoryId(int productId, int categoryId) {
		this.productId = productId;
		this.categoryId = categoryId;
	}

	@Column(name = "product_id", nullable = false)
	public int getProductId() {
		return this.productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	@Column(name = "category_id", nullable = false)
	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ProductToCategoryId))
			return false;
		ProductToCategoryId castOther = (ProductToCategoryId) other;

		return (this.getProductId() == castOther.getProductId())
				&& (this.getCategoryId() == castOther.getCategoryId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getProductId();
		result = 37 * result + this.getCategoryId();
		return result;
	}

}
