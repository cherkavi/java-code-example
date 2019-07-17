package com.cherkashyn.vitalii.eshops.opencart.uploader.domain;

public class Product {
	private int id;
	private String model;
	private String[] images;
	private String name;
	private String	description;
	private String	metaDescription;
	private String	metaKeyword;
	private String	tag;
	private float	price;
	private String	image;
	private int	quantity;
	
	public int getId() {
		return id;
	}

	public void setId(int int1) {
		this.id=int1;
	}

	public String getModel() {
		return model;
	}

	public String[] getImages(){
		return images;
	}

	public String getName() {
		return name;
	}

	public int getLanguageId() {
		return 1;
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

	public String getTag() {
		return tag;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getImage() {
		return image;
	}

	public float getPrice() {
		return price;
	}

	public Product(String model, String name,
			String description, String metaDescription, String metaKeyword,
			String tag, float price, String image, int quantity, String ... images) {
		this(0, model, name, description, metaDescription, metaKeyword, tag, price, image, quantity, images);
	}

	public Product(int id, String model, String name,
			String description, String metaDescription, String metaKeyword,
			String tag, float price, String image, int quantity, String ... images) {
		super();
		this.id=id;
		this.model = model;
		this.images = images;
		this.name = name;
		this.description = description;
		this.metaDescription = metaDescription;
		this.metaKeyword = metaKeyword;
		this.tag = tag;
		this.price = price;
		this.image = image;
		this.quantity = quantity;
	}
}
