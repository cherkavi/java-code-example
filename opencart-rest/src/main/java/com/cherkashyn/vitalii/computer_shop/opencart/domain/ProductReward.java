package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OcProductReward generated by hbm2java
 */
@Entity
@Table(name = "oc_product_reward", catalog = "opencart")
public class ProductReward implements java.io.Serializable {

	private Integer productRewardId;
	private int productId;
	private int customerGroupId;
	private int points;

	public ProductReward() {
	}

	public ProductReward(int productId, int customerGroupId, int points) {
		this.productId = productId;
		this.customerGroupId = customerGroupId;
		this.points = points;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "product_reward_id", unique = true, nullable = false)
	public Integer getProductRewardId() {
		return this.productRewardId;
	}

	public void setProductRewardId(Integer productRewardId) {
		this.productRewardId = productRewardId;
	}

	@Column(name = "product_id", nullable = false)
	public int getProductId() {
		return this.productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	@Column(name = "customer_group_id", nullable = false)
	public int getCustomerGroupId() {
		return this.customerGroupId;
	}

	public void setCustomerGroupId(int customerGroupId) {
		this.customerGroupId = customerGroupId;
	}

	@Column(name = "points", nullable = false)
	public int getPoints() {
		return this.points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

}
