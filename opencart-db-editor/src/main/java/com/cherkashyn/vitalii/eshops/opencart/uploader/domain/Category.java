package com.cherkashyn.vitalii.eshops.opencart.uploader.domain;

import java.sql.Date;

import org.apache.commons.lang.StringUtils;

public class Category {
	private int id;
	private Date dateAdd;
	private Date dateModified;
	private String name;
	private String	description;
	private String	metaDescription;
	private String	metaKeyword;

	private int languageId=1;
	private int status=1;
	private int sortOrder=0;
	private int top=1;
	private int column=1;
	private int parentId=0;
	private String image=StringUtils.EMPTY;

	public Category(){
		dateAdd=new Date(new java.util.Date().getTime());
		dateModified=dateAdd;
	}
	
	public Category(String name,
			String description, String metaDescription, String metaKeyword) {
		this(0, name, description, metaDescription, metaKeyword);
	}

	public Category(int id, String name,
			String description, String metaDescription, String metaKeyword) {
		this();
		this.id=id;
		this.name = name;
		this.description = description;
		this.metaDescription = metaDescription;
		this.metaKeyword = metaKeyword;
	}

	/** path to image, 
	 * example: data/demo/hp_2.jpg */
	public String getImage() {
		return image;
	}                                                                                                                                                                                                

	/**
	 * id of parent element, 
	 * @return 0, if one of root element
	 */
	public int getParentId() {
		return parentId;
	}

	public int getTop() {
		return top;
	}

	public int getColumn() {
		return column;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public int getStatus() {
		return status;
	}

	
	public void setId(int id) {
		this.id=id;
	}
	
	public int getId(){
		return this.id;
	}

	public Date getDateAdded() {
		return dateAdd;
	}

	public Date getDateModified() {
		return dateModified;
	}

	/**
	 * return default Language Id
	 * @return
	 */
	public int getLanguageId() {
		return languageId;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public String getMetaKeyword() {
		return metaKeyword;
	}

	public Date getDateAdd() {
		return dateAdd;
	}

	public void setDateAdd(Date dateAdd) {
		this.dateAdd = dateAdd;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public void setMetaKeyword(String metaKeyword) {
		this.metaKeyword = metaKeyword;
	}
}
