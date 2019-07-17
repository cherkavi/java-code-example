package com.cherkashyn.vitalii.parsers.spare_parts.model;

/**
 * describe all elements for certain position
 */
public class PositionModel {
	private String describePageUrl;
	private String imageUrl;
	private String imageBigUrl;
	
	private String kodScarab;
	private String kodCatalog;
	private String kodObhodniy;
	private String kodVirobniy;
	private String kodSkp;
	public String getDescribePageUrl() {
		return describePageUrl;
	}
	public void setDescribePageUrl(String describePageUrl) {
		this.describePageUrl = describePageUrl;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getImageBigUrl() {
		return imageBigUrl;
	}
	public void setImageBigUrl(String imageBigUrl) {
		this.imageBigUrl = imageBigUrl;
	}
	public String getKodScarab() {
		return kodScarab;
	}
	public void setKodScarab(String kodScarab) {
		this.kodScarab = kodScarab;
	}
	public String getKodCatalog() {
		return kodCatalog;
	}
	public void setKodCatalog(String kodCatalog) {
		this.kodCatalog = kodCatalog;
	}
	public String getKodObhodniy() {
		return kodObhodniy;
	}
	public void setKodObhodniy(String kodObhodniy) {
		this.kodObhodniy = kodObhodniy;
	}
	public String getKodVirobniy() {
		return kodVirobniy;
	}
	public void setKodVirobniy(String kodVirobniy) {
		this.kodVirobniy = kodVirobniy;
	}
	public String getKodSkp() {
		return kodSkp;
	}
	public void setKodSkp(String kodSkp) {
		this.kodSkp = kodSkp;
	}
	@Override
	public String toString() {
		return "PositionModel [describePageUrl=" + describePageUrl
				+ ", imageBigUrl=" + imageBigUrl + ", imageUrl=" + imageUrl
				+ ", kodCatalog=" + kodCatalog + ", kodObhodniy=" + kodObhodniy
				+ ", kodScarab=" + kodScarab + ", kodSkp=" + kodSkp
				+ ", kodVirobniy=" + kodVirobniy + "]";
	}
	
	
}
