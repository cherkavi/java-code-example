package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OcBannerImage generated by hbm2java
 */
@Entity
@Table(name = "oc_banner_image", catalog = "opencart")
public class BannerImage implements java.io.Serializable {

	private Integer bannerImageId;
	private int bannerId;
	private String link;
	private String image;

	public BannerImage() {
	}

	public BannerImage(int bannerId, String link, String image) {
		this.bannerId = bannerId;
		this.link = link;
		this.image = image;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "banner_image_id", unique = true, nullable = false)
	public Integer getBannerImageId() {
		return this.bannerImageId;
	}

	public void setBannerImageId(Integer bannerImageId) {
		this.bannerImageId = bannerImageId;
	}

	@Column(name = "banner_id", nullable = false)
	public int getBannerId() {
		return this.bannerId;
	}

	public void setBannerId(int bannerId) {
		this.bannerId = bannerId;
	}

	@Column(name = "link", nullable = false)
	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Column(name = "image", nullable = false)
	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
