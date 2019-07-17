package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OcCustomerBanIp generated by hbm2java
 */
@Entity
@Table(name = "oc_customer_ban_ip", catalog = "opencart")
public class CustomerBanIp implements java.io.Serializable {

	private Integer customerBanIpId;
	private String ip;

	public CustomerBanIp() {
	}

	public CustomerBanIp(String ip) {
		this.ip = ip;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "customer_ban_ip_id", unique = true, nullable = false)
	public Integer getCustomerBanIpId() {
		return this.customerBanIpId;
	}

	public void setCustomerBanIpId(Integer customerBanIpId) {
		this.customerBanIpId = customerBanIpId;
	}

	@Column(name = "ip", nullable = false, length = 40)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
