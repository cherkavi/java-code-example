package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * OcInformationToStoreId generated by hbm2java
 */
@Embeddable
public class InformationToStoreId implements java.io.Serializable {

	private int informationId;
	private int storeId;

	public InformationToStoreId() {
	}

	public InformationToStoreId(int informationId, int storeId) {
		this.informationId = informationId;
		this.storeId = storeId;
	}

	@Column(name = "information_id", nullable = false)
	public int getInformationId() {
		return this.informationId;
	}

	public void setInformationId(int informationId) {
		this.informationId = informationId;
	}

	@Column(name = "store_id", nullable = false)
	public int getStoreId() {
		return this.storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof InformationToStoreId))
			return false;
		InformationToStoreId castOther = (InformationToStoreId) other;

		return (this.getInformationId() == castOther.getInformationId())
				&& (this.getStoreId() == castOther.getStoreId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getInformationId();
		result = 37 * result + this.getStoreId();
		return result;
	}

}
