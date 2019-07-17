package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * OcReturnActionId generated by hbm2java
 */
@Embeddable
public class ReturnActionId implements java.io.Serializable {

	private int returnActionId;
	private int languageId;

	public ReturnActionId() {
	}

	public ReturnActionId(int returnActionId, int languageId) {
		this.returnActionId = returnActionId;
		this.languageId = languageId;
	}

	@Column(name = "return_action_id", nullable = false)
	public int getReturnActionId() {
		return this.returnActionId;
	}

	public void setReturnActionId(int returnActionId) {
		this.returnActionId = returnActionId;
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
		if (!(other instanceof ReturnActionId))
			return false;
		ReturnActionId castOther = (ReturnActionId) other;

		return (this.getReturnActionId() == castOther.getReturnActionId())
				&& (this.getLanguageId() == castOther.getLanguageId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getReturnActionId();
		result = 37 * result + this.getLanguageId();
		return result;
	}

}
