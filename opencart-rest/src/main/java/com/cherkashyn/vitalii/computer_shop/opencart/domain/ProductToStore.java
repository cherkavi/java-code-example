package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * OcProductToStore generated by hbm2java
 */
@Entity
@Table(name = "oc_product_to_store", catalog = "opencart")
public class ProductToStore implements java.io.Serializable {

	private ProductToStoreId id;

	public ProductToStore() {
	}

	public ProductToStore(ProductToStoreId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "productId", column = @Column(name = "product_id", nullable = false)),
			@AttributeOverride(name = "storeId", column = @Column(name = "store_id", nullable = false)) })
	public ProductToStoreId getId() {
		return this.id;
	}

	public void setId(ProductToStoreId id) {
		this.id = id;
	}

}
