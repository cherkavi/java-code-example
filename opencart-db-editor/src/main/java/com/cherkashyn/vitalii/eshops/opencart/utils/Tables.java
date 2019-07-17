package com.cherkashyn.vitalii.eshops.opencart.utils;

public class Tables {
	public static String PREFIX="oc_";
	
	public static String category(){
		return PREFIX+"category";
	}

	public static String categoryDescription(){
		return PREFIX+"category_description";
	}
	
	public static String categoryPath(){
		return PREFIX+"category_path";
	}
	
	public static String categoryToStore(){
		return PREFIX+"category_to_store";
	}

	public static String product() {
		return PREFIX+"product";
	}

	public static String productImage() {
		return PREFIX+"product_image";
	}
	
	public static String productReward() {
		return PREFIX+"product_reward";
	}
	
	public static String productToStore() {
		return PREFIX+"product_to_store";
	}

	public static String productDescription() {
		return PREFIX+"product_description";
	}

	public static String productToCategory() {
		return PREFIX+"product_to_category";
	}
}
