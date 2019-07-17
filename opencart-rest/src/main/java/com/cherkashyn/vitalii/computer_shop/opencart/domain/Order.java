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
 * OcOrder generated by hbm2java
 */
@Entity
@Table(name = "oc_order", catalog = "opencart")
public class Order implements java.io.Serializable {

	private Integer orderId;
	private int invoiceNo;
	private String invoicePrefix;
	private int storeId;
	private String storeName;
	private String storeUrl;
	private int customerId;
	private int customerGroupId;
	private String firstname;
	private String lastname;
	private String email;
	private String telephone;
	private String fax;
	private String paymentFirstname;
	private String paymentLastname;
	private String paymentCompany;
	private String paymentCompanyId;
	private String paymentTaxId;
	private String paymentAddress1;
	private String paymentAddress2;
	private String paymentCity;
	private String paymentPostcode;
	private String paymentCountry;
	private int paymentCountryId;
	private String paymentZone;
	private int paymentZoneId;
	private String paymentAddressFormat;
	private String paymentMethod;
	private String paymentCode;
	private String shippingFirstname;
	private String shippingLastname;
	private String shippingCompany;
	private String shippingAddress1;
	private String shippingAddress2;
	private String shippingCity;
	private String shippingPostcode;
	private String shippingCountry;
	private int shippingCountryId;
	private String shippingZone;
	private int shippingZoneId;
	private String shippingAddressFormat;
	private String shippingMethod;
	private String shippingCode;
	private String comment;
	private BigDecimal total;
	private int orderStatusId;
	private int affiliateId;
	private BigDecimal commission;
	private int languageId;
	private int currencyId;
	private String currencyCode;
	private BigDecimal currencyValue;
	private String ip;
	private String forwardedIp;
	private String userAgent;
	private String acceptLanguage;
	private Date dateAdded;
	private Date dateModified;

	public Order() {
	}

	public Order(int invoiceNo, String invoicePrefix, int storeId,
			String storeName, String storeUrl, int customerId,
			int customerGroupId, String firstname, String lastname,
			String email, String telephone, String fax,
			String paymentFirstname, String paymentLastname,
			String paymentCompany, String paymentCompanyId,
			String paymentTaxId, String paymentAddress1,
			String paymentAddress2, String paymentCity, String paymentPostcode,
			String paymentCountry, int paymentCountryId, String paymentZone,
			int paymentZoneId, String paymentAddressFormat,
			String paymentMethod, String paymentCode, String shippingFirstname,
			String shippingLastname, String shippingCompany,
			String shippingAddress1, String shippingAddress2,
			String shippingCity, String shippingPostcode,
			String shippingCountry, int shippingCountryId, String shippingZone,
			int shippingZoneId, String shippingAddressFormat,
			String shippingMethod, String shippingCode, String comment,
			BigDecimal total, int orderStatusId, int affiliateId,
			BigDecimal commission, int languageId, int currencyId,
			String currencyCode, BigDecimal currencyValue, String ip,
			String forwardedIp, String userAgent, String acceptLanguage,
			Date dateAdded, Date dateModified) {
		this.invoiceNo = invoiceNo;
		this.invoicePrefix = invoicePrefix;
		this.storeId = storeId;
		this.storeName = storeName;
		this.storeUrl = storeUrl;
		this.customerId = customerId;
		this.customerGroupId = customerGroupId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.telephone = telephone;
		this.fax = fax;
		this.paymentFirstname = paymentFirstname;
		this.paymentLastname = paymentLastname;
		this.paymentCompany = paymentCompany;
		this.paymentCompanyId = paymentCompanyId;
		this.paymentTaxId = paymentTaxId;
		this.paymentAddress1 = paymentAddress1;
		this.paymentAddress2 = paymentAddress2;
		this.paymentCity = paymentCity;
		this.paymentPostcode = paymentPostcode;
		this.paymentCountry = paymentCountry;
		this.paymentCountryId = paymentCountryId;
		this.paymentZone = paymentZone;
		this.paymentZoneId = paymentZoneId;
		this.paymentAddressFormat = paymentAddressFormat;
		this.paymentMethod = paymentMethod;
		this.paymentCode = paymentCode;
		this.shippingFirstname = shippingFirstname;
		this.shippingLastname = shippingLastname;
		this.shippingCompany = shippingCompany;
		this.shippingAddress1 = shippingAddress1;
		this.shippingAddress2 = shippingAddress2;
		this.shippingCity = shippingCity;
		this.shippingPostcode = shippingPostcode;
		this.shippingCountry = shippingCountry;
		this.shippingCountryId = shippingCountryId;
		this.shippingZone = shippingZone;
		this.shippingZoneId = shippingZoneId;
		this.shippingAddressFormat = shippingAddressFormat;
		this.shippingMethod = shippingMethod;
		this.shippingCode = shippingCode;
		this.comment = comment;
		this.total = total;
		this.orderStatusId = orderStatusId;
		this.affiliateId = affiliateId;
		this.commission = commission;
		this.languageId = languageId;
		this.currencyId = currencyId;
		this.currencyCode = currencyCode;
		this.currencyValue = currencyValue;
		this.ip = ip;
		this.forwardedIp = forwardedIp;
		this.userAgent = userAgent;
		this.acceptLanguage = acceptLanguage;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "order_id", unique = true, nullable = false)
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Column(name = "invoice_no", nullable = false)
	public int getInvoiceNo() {
		return this.invoiceNo;
	}

	public void setInvoiceNo(int invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@Column(name = "invoice_prefix", nullable = false, length = 26)
	public String getInvoicePrefix() {
		return this.invoicePrefix;
	}

	public void setInvoicePrefix(String invoicePrefix) {
		this.invoicePrefix = invoicePrefix;
	}

	@Column(name = "store_id", nullable = false)
	public int getStoreId() {
		return this.storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	@Column(name = "store_name", nullable = false, length = 64)
	public String getStoreName() {
		return this.storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	@Column(name = "store_url", nullable = false)
	public String getStoreUrl() {
		return this.storeUrl;
	}

	public void setStoreUrl(String storeUrl) {
		this.storeUrl = storeUrl;
	}

	@Column(name = "customer_id", nullable = false)
	public int getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	@Column(name = "customer_group_id", nullable = false)
	public int getCustomerGroupId() {
		return this.customerGroupId;
	}

	public void setCustomerGroupId(int customerGroupId) {
		this.customerGroupId = customerGroupId;
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

	@Column(name = "payment_firstname", nullable = false, length = 32)
	public String getPaymentFirstname() {
		return this.paymentFirstname;
	}

	public void setPaymentFirstname(String paymentFirstname) {
		this.paymentFirstname = paymentFirstname;
	}

	@Column(name = "payment_lastname", nullable = false, length = 32)
	public String getPaymentLastname() {
		return this.paymentLastname;
	}

	public void setPaymentLastname(String paymentLastname) {
		this.paymentLastname = paymentLastname;
	}

	@Column(name = "payment_company", nullable = false, length = 32)
	public String getPaymentCompany() {
		return this.paymentCompany;
	}

	public void setPaymentCompany(String paymentCompany) {
		this.paymentCompany = paymentCompany;
	}

	@Column(name = "payment_company_id", nullable = false, length = 32)
	public String getPaymentCompanyId() {
		return this.paymentCompanyId;
	}

	public void setPaymentCompanyId(String paymentCompanyId) {
		this.paymentCompanyId = paymentCompanyId;
	}

	@Column(name = "payment_tax_id", nullable = false, length = 32)
	public String getPaymentTaxId() {
		return this.paymentTaxId;
	}

	public void setPaymentTaxId(String paymentTaxId) {
		this.paymentTaxId = paymentTaxId;
	}

	@Column(name = "payment_address_1", nullable = false, length = 128)
	public String getPaymentAddress1() {
		return this.paymentAddress1;
	}

	public void setPaymentAddress1(String paymentAddress1) {
		this.paymentAddress1 = paymentAddress1;
	}

	@Column(name = "payment_address_2", nullable = false, length = 128)
	public String getPaymentAddress2() {
		return this.paymentAddress2;
	}

	public void setPaymentAddress2(String paymentAddress2) {
		this.paymentAddress2 = paymentAddress2;
	}

	@Column(name = "payment_city", nullable = false, length = 128)
	public String getPaymentCity() {
		return this.paymentCity;
	}

	public void setPaymentCity(String paymentCity) {
		this.paymentCity = paymentCity;
	}

	@Column(name = "payment_postcode", nullable = false, length = 10)
	public String getPaymentPostcode() {
		return this.paymentPostcode;
	}

	public void setPaymentPostcode(String paymentPostcode) {
		this.paymentPostcode = paymentPostcode;
	}

	@Column(name = "payment_country", nullable = false, length = 128)
	public String getPaymentCountry() {
		return this.paymentCountry;
	}

	public void setPaymentCountry(String paymentCountry) {
		this.paymentCountry = paymentCountry;
	}

	@Column(name = "payment_country_id", nullable = false)
	public int getPaymentCountryId() {
		return this.paymentCountryId;
	}

	public void setPaymentCountryId(int paymentCountryId) {
		this.paymentCountryId = paymentCountryId;
	}

	@Column(name = "payment_zone", nullable = false, length = 128)
	public String getPaymentZone() {
		return this.paymentZone;
	}

	public void setPaymentZone(String paymentZone) {
		this.paymentZone = paymentZone;
	}

	@Column(name = "payment_zone_id", nullable = false)
	public int getPaymentZoneId() {
		return this.paymentZoneId;
	}

	public void setPaymentZoneId(int paymentZoneId) {
		this.paymentZoneId = paymentZoneId;
	}

	@Column(name = "payment_address_format", nullable = false, length = 65535)
	public String getPaymentAddressFormat() {
		return this.paymentAddressFormat;
	}

	public void setPaymentAddressFormat(String paymentAddressFormat) {
		this.paymentAddressFormat = paymentAddressFormat;
	}

	@Column(name = "payment_method", nullable = false, length = 128)
	public String getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Column(name = "payment_code", nullable = false, length = 128)
	public String getPaymentCode() {
		return this.paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	@Column(name = "shipping_firstname", nullable = false, length = 32)
	public String getShippingFirstname() {
		return this.shippingFirstname;
	}

	public void setShippingFirstname(String shippingFirstname) {
		this.shippingFirstname = shippingFirstname;
	}

	@Column(name = "shipping_lastname", nullable = false, length = 32)
	public String getShippingLastname() {
		return this.shippingLastname;
	}

	public void setShippingLastname(String shippingLastname) {
		this.shippingLastname = shippingLastname;
	}

	@Column(name = "shipping_company", nullable = false, length = 32)
	public String getShippingCompany() {
		return this.shippingCompany;
	}

	public void setShippingCompany(String shippingCompany) {
		this.shippingCompany = shippingCompany;
	}

	@Column(name = "shipping_address_1", nullable = false, length = 128)
	public String getShippingAddress1() {
		return this.shippingAddress1;
	}

	public void setShippingAddress1(String shippingAddress1) {
		this.shippingAddress1 = shippingAddress1;
	}

	@Column(name = "shipping_address_2", nullable = false, length = 128)
	public String getShippingAddress2() {
		return this.shippingAddress2;
	}

	public void setShippingAddress2(String shippingAddress2) {
		this.shippingAddress2 = shippingAddress2;
	}

	@Column(name = "shipping_city", nullable = false, length = 128)
	public String getShippingCity() {
		return this.shippingCity;
	}

	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}

	@Column(name = "shipping_postcode", nullable = false, length = 10)
	public String getShippingPostcode() {
		return this.shippingPostcode;
	}

	public void setShippingPostcode(String shippingPostcode) {
		this.shippingPostcode = shippingPostcode;
	}

	@Column(name = "shipping_country", nullable = false, length = 128)
	public String getShippingCountry() {
		return this.shippingCountry;
	}

	public void setShippingCountry(String shippingCountry) {
		this.shippingCountry = shippingCountry;
	}

	@Column(name = "shipping_country_id", nullable = false)
	public int getShippingCountryId() {
		return this.shippingCountryId;
	}

	public void setShippingCountryId(int shippingCountryId) {
		this.shippingCountryId = shippingCountryId;
	}

	@Column(name = "shipping_zone", nullable = false, length = 128)
	public String getShippingZone() {
		return this.shippingZone;
	}

	public void setShippingZone(String shippingZone) {
		this.shippingZone = shippingZone;
	}

	@Column(name = "shipping_zone_id", nullable = false)
	public int getShippingZoneId() {
		return this.shippingZoneId;
	}

	public void setShippingZoneId(int shippingZoneId) {
		this.shippingZoneId = shippingZoneId;
	}

	@Column(name = "shipping_address_format", nullable = false, length = 65535)
	public String getShippingAddressFormat() {
		return this.shippingAddressFormat;
	}

	public void setShippingAddressFormat(String shippingAddressFormat) {
		this.shippingAddressFormat = shippingAddressFormat;
	}

	@Column(name = "shipping_method", nullable = false, length = 128)
	public String getShippingMethod() {
		return this.shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	@Column(name = "shipping_code", nullable = false, length = 128)
	public String getShippingCode() {
		return this.shippingCode;
	}

	public void setShippingCode(String shippingCode) {
		this.shippingCode = shippingCode;
	}

	@Column(name = "comment", nullable = false, length = 65535)
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column(name = "total", nullable = false, precision = 15, scale = 4)
	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	@Column(name = "order_status_id", nullable = false)
	public int getOrderStatusId() {
		return this.orderStatusId;
	}

	public void setOrderStatusId(int orderStatusId) {
		this.orderStatusId = orderStatusId;
	}

	@Column(name = "affiliate_id", nullable = false)
	public int getAffiliateId() {
		return this.affiliateId;
	}

	public void setAffiliateId(int affiliateId) {
		this.affiliateId = affiliateId;
	}

	@Column(name = "commission", nullable = false, precision = 15, scale = 4)
	public BigDecimal getCommission() {
		return this.commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	@Column(name = "language_id", nullable = false)
	public int getLanguageId() {
		return this.languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	@Column(name = "currency_id", nullable = false)
	public int getCurrencyId() {
		return this.currencyId;
	}

	public void setCurrencyId(int currencyId) {
		this.currencyId = currencyId;
	}

	@Column(name = "currency_code", nullable = false, length = 3)
	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Column(name = "currency_value", nullable = false, precision = 15, scale = 8)
	public BigDecimal getCurrencyValue() {
		return this.currencyValue;
	}

	public void setCurrencyValue(BigDecimal currencyValue) {
		this.currencyValue = currencyValue;
	}

	@Column(name = "ip", nullable = false, length = 40)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "forwarded_ip", nullable = false, length = 40)
	public String getForwardedIp() {
		return this.forwardedIp;
	}

	public void setForwardedIp(String forwardedIp) {
		this.forwardedIp = forwardedIp;
	}

	@Column(name = "user_agent", nullable = false)
	public String getUserAgent() {
		return this.userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	@Column(name = "accept_language", nullable = false)
	public String getAcceptLanguage() {
		return this.acceptLanguage;
	}

	public void setAcceptLanguage(String acceptLanguage) {
		this.acceptLanguage = acceptLanguage;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_added", nullable = false, length = 19)
	public Date getDateAdded() {
		return this.dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_modified", nullable = false, length = 19)
	public Date getDateModified() {
		return this.dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

}
