package com.cherkashyn.vitalii.accounting.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SERIAL")
public class Serial {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = true)
	private Integer		id;

	@Column(name = "BAR_CODE")
	private String		barCode;

	@Column(name = "BAR_CODE_SELLER")
	private String		barCodeSeller;

	@Column(name = "CREATEDAT")
	private Date		createdat	= new Date();

	@ManyToOne
	@JoinColumn(name = "SUPPLIER_ID")
	private Supplier	supplier;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return barCode;
	}

	public void setCode(String code) {
		this.barCode = code;
	}

	public String getCodeSeller() {
		return barCodeSeller;
	}

	public void setCodeSeller(String codeSeller) {
		this.barCodeSeller = codeSeller;
	}

	public Date getCreatedat() {
		return createdat;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

}
