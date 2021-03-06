package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * OcAffiliate generated by hbm2java
 */
@Entity
@Table(name = "oc_affiliate", catalog = "opencart")
public class Affiliate implements java.io.Serializable {

	private Integer affiliateId;
	private String firstname;
	private String lastname;
	private String email;
	private String telephone;
	private String fax;
	private String password;
	private String salt;
	private String company;
	private String website;
	private String address1;
	private String address2;
	private String city;
	private String postcode;
	private int countryId;
	private int zoneId;
	private String code;
	private BigDecimal commission;
	private String tax;
	private String payment;
	private String cheque;
	private String paypal;
	private String bankName;
	private String bankBranchNumber;
	private String bankSwiftCode;
	private String bankAccountName;
	private String bankAccountNumber;
	private String ip;
	private boolean status;
	private boolean approved;
	private Date dateAdded;

	public Affiliate() {
	}

	public Affiliate(String firstname, String lastname, String email,
			String telephone, String fax, String password, String salt,
			String company, String website, String address1, String address2,
			String city, String postcode, int countryId, int zoneId,
			String code, BigDecimal commission, String tax, String payment,
			String cheque, String paypal, String bankName,
			String bankBranchNumber, String bankSwiftCode,
			String bankAccountName, String bankAccountNumber, String ip,
			boolean status, boolean approved, Date dateAdded) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.telephone = telephone;
		this.fax = fax;
		this.password = password;
		this.salt = salt;
		this.company = company;
		this.website = website;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.postcode = postcode;
		this.countryId = countryId;
		this.zoneId = zoneId;
		this.code = code;
		this.commission = commission;
		this.tax = tax;
		this.payment = payment;
		this.cheque = cheque;
		this.paypal = paypal;
		this.bankName = bankName;
		this.bankBranchNumber = bankBranchNumber;
		this.bankSwiftCode = bankSwiftCode;
		this.bankAccountName = bankAccountName;
		this.bankAccountNumber = bankAccountNumber;
		this.ip = ip;
		this.status = status;
		this.approved = approved;
		this.dateAdded = dateAdded;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "affiliate_id", unique = true, nullable = false)
	public Integer getAffiliateId() {
		return this.affiliateId;
	}

	public void setAffiliateId(Integer affiliateId) {
		this.affiliateId = affiliateId;
	}

	@Column(name = "firstname", nullable = false, length = 32)
	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Column(name = "lastname", nullable = false, length = 32)
	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Column(name = "email", nullable = false, length = 96)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "telephone", nullable = false, length = 32)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "fax", nullable = false, length = 32)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "password", nullable = false, length = 40)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "salt", nullable = false, length = 9)
	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Column(name = "company", nullable = false, length = 32)
	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "website", nullable = false)
	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@Column(name = "address_1", nullable = false, length = 128)
	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	@Column(name = "address_2", nullable = false, length = 128)
	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	@Column(name = "city", nullable = false, length = 128)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "postcode", nullable = false, length = 10)
	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	@Column(name = "country_id", nullable = false)
	public int getCountryId() {
		return this.countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	@Column(name = "zone_id", nullable = false)
	public int getZoneId() {
		return this.zoneId;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}

	@Column(name = "code", nullable = false, length = 64)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "commission", nullable = false, precision = 4)
	public BigDecimal getCommission() {
		return this.commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	@Column(name = "tax", nullable = false, length = 64)
	public String getTax() {
		return this.tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	@Column(name = "payment", nullable = false, length = 6)
	public String getPayment() {
		return this.payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	@Column(name = "cheque", nullable = false, length = 100)
	public String getCheque() {
		return this.cheque;
	}

	public void setCheque(String cheque) {
		this.cheque = cheque;
	}

	@Column(name = "paypal", nullable = false, length = 64)
	public String getPaypal() {
		return this.paypal;
	}

	public void setPaypal(String paypal) {
		this.paypal = paypal;
	}

	@Column(name = "bank_name", nullable = false, length = 64)
	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "bank_branch_number", nullable = false, length = 64)
	public String getBankBranchNumber() {
		return this.bankBranchNumber;
	}

	public void setBankBranchNumber(String bankBranchNumber) {
		this.bankBranchNumber = bankBranchNumber;
	}

	@Column(name = "bank_swift_code", nullable = false, length = 64)
	public String getBankSwiftCode() {
		return this.bankSwiftCode;
	}

	public void setBankSwiftCode(String bankSwiftCode) {
		this.bankSwiftCode = bankSwiftCode;
	}

	@Column(name = "bank_account_name", nullable = false, length = 64)
	public String getBankAccountName() {
		return this.bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	@Column(name = "bank_account_number", nullable = false, length = 64)
	public String getBankAccountNumber() {
		return this.bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	@Column(name = "ip", nullable = false, length = 40)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "status", nullable = false)
	public boolean isStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Column(name = "approved", nullable = false)
	public boolean isApproved() {
		return this.approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_added", nullable = false, length = 19)
	public Date getDateAdded() {
		return this.dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

}
