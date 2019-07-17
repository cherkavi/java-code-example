package com.cherkashyn.vitalii.computer_shop.opencart.rest;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.computer_shop.opencart.domain.Category;
import com.cherkashyn.vitalii.computer_shop.opencart.service.declaration.CategoryService;
import com.cherkashyn.vitalii.computer_shop.opencart.service.declaration.LanguageService;
import com.cherkashyn.vitalii.computer_shop.opencart.service.exception.ServiceException;

@Component
@Path("/category")
/**
 * rest service for CRUD operation with Category
 */
public class CategoryRest {
	
	@Autowired(required=true)
	CategoryService service;

	@Autowired(required=true)
	LanguageService serviceLanguage;
	
	/**
	 * find category by id 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Category find(@PathParam("id") Integer id) throws ServiceException{
		Category returnValue=service.find(id);
		return returnValue;
	}

	/**
	 * find category by id 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@GET
	@Path("/find")
	@Produces("application/json")
	public Category findByName(@QueryParam("name") String name) throws ServiceException{
		Category returnValue=service.find(name);
		return returnValue;
	}
	
	@POST
	@Path("/create")
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	@Consumes(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED)
	/**
	 * create category 
	 * @param categoryName
	 * @param languageName ( @Nullable )
	 * @return
	 * @throws ServiceException
	 */
	public Category create(@FormParam("name") String categoryName, @FormParam("lang") String languageName) throws ServiceException{
		if(languageName==null){
			return this.service.create(createCategory(), categoryName, serviceLanguage.findDefaultLanguage());
		}else{
			return this.service.create(createCategory(), categoryName, serviceLanguage.find(languageName) );
		}
		
	}

	
	private Category createCategory(){
		Category category=new Category();
		category.setImage(StringUtils.EMPTY);
		category.setParentId(0);
		category.setTop(false);
		category.setColumn(1);
		category.setSortOrder(0);
		category.setStatus(true);
		Date date=new Date();
		category.setDateAdded(date);
		category.setDateModified(date);
		return category;
	}
}
