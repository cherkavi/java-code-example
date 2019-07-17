package com.cherkashyn.vitalii.computer_shop.opencart.rest;

import java.math.BigDecimal;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.computer_shop.opencart.domain.Product;
import com.cherkashyn.vitalii.computer_shop.opencart.service.declaration.LanguageService;
import com.cherkashyn.vitalii.computer_shop.opencart.service.declaration.ProductService;
import com.cherkashyn.vitalii.computer_shop.opencart.service.exception.ServiceException;

@Component
@Path("/product")
/**
 * rest service for CRUD operation with Product
 */
public class ProductRest {
	
	@Autowired(required=true)
	ProductService service;

	@Autowired
	LanguageService serviceLanguage;
	

	@GET
	@Path("/{id}")
	@Produces("application/json")
	/**
	 * find product by id 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public Product find(@PathParam("id") Integer id) throws ServiceException{
		Product returnValue=service.find(id);
		return returnValue;
	}
	
	@POST
	@Path("/create")
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	@Consumes(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED)

	public Product create(	@FormParam("categoryId") int categoryId,
							@FormParam("productName") String productName, 
							@FormParam("quantity") int quantity, 
							@FormParam("imageName") String imageName, 
							@FormParam("price") BigDecimal price,  
							@FormParam("lang") String languageName) 
									throws ServiceException{
		if(languageName==null){
			return this.service.create(categoryId, createProduct(quantity, imageName, price), productName, serviceLanguage.findDefaultLanguage());
		}else{
			return this.service.create(categoryId, createProduct(quantity, imageName, price), productName, serviceLanguage.find(languageName));
		}
		
	}

	private Product createProduct(int quantity, String imageName, BigDecimal price) {
		Date date=new Date();
		Product product=new Product();
		product.setModel(StringUtils.EMPTY); // modelName
		product.setSku(StringUtils.EMPTY);
		product.setUpc(StringUtils.EMPTY);
		product.setEan(StringUtils.EMPTY);
		product.setJan(StringUtils.EMPTY);
		product.setIsbn(StringUtils.EMPTY);
		product.setMpn(StringUtils.EMPTY);
		product.setLocation(StringUtils.EMPTY);
		product.setQuantity(quantity);
		// product.setStockStatusId();// find StockStatusId
		product.setImage(imageName);
		product.setManufacturerId(0);// ?
		product.setShipping(true);
		product.setPrice(price);
		product.setPoints(0);
		product.setTaxClassId(0);
		product.setDateAvailable(date);
		product.setWeight(BigDecimal.ZERO);
		product.setWeightClassId(1);
		product.setLength(BigDecimal.ZERO);
		product.setWidth(BigDecimal.ZERO);
		product.setHeight(BigDecimal.ZERO);
		product.setLengthClassId(1);
		product.setSubtract(true);
		product.setMinimum(1);
		product.setSortOrder(1);
		product.setStatus(true);
		product.setDateAdded(date);
		product.setDateModified(date);
		product.setViewed(0);
		return product;
	}

	
}
