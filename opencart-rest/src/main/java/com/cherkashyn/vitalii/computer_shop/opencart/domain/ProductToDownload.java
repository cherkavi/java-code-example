package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * OcProductToDownload generated by hbm2java
 */
@Entity
@Table(name = "oc_product_to_download", catalog = "opencart")
public class ProductToDownload implements java.io.Serializable {

	private ProductToDownloadId id;

	public ProductToDownload() {
	}

	public ProductToDownload(ProductToDownloadId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "productId", column = @Column(name = "product_id", nullable = false)),
			@AttributeOverride(name = "downloadId", column = @Column(name = "download_id", nullable = false)) })
	public ProductToDownloadId getId() {
		return this.id;
	}

	public void setId(ProductToDownloadId id) {
		this.id = id;
	}

}
