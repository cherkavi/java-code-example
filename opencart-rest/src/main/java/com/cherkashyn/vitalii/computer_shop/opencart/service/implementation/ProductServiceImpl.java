package com.cherkashyn.vitalii.computer_shop.opencart.service.implementation;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cherkashyn.vitalii.computer_shop.opencart.domain.Language;
import com.cherkashyn.vitalii.computer_shop.opencart.domain.Product;
import com.cherkashyn.vitalii.computer_shop.opencart.domain.ProductDescription;
import com.cherkashyn.vitalii.computer_shop.opencart.domain.ProductDescriptionId;
import com.cherkashyn.vitalii.computer_shop.opencart.domain.ProductToCategory;
import com.cherkashyn.vitalii.computer_shop.opencart.domain.ProductToCategoryId;
import com.cherkashyn.vitalii.computer_shop.opencart.domain.ProductToStore;
import com.cherkashyn.vitalii.computer_shop.opencart.domain.ProductToStoreId;
import com.cherkashyn.vitalii.computer_shop.opencart.service.declaration.ProductService;
import com.cherkashyn.vitalii.computer_shop.opencart.service.exception.ServiceException;
import com.cherkashyn.vitalii.computer_shop.opencart.service.helper.SessionExecutor;

@Service("opencart.ProductService")
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	SessionFactory factory;


	public Product find(final int id) throws ServiceException{
		return new SessionExecutor<Product>(factory, "can't get Product by Id ") {
			@Override
			protected Product logic(Session session) throws HibernateException, ServiceException {
				Query query=session.createQuery("from "+Product.class.getSimpleName()+" where Product_id=:id");
				query.setParameter("id", id);
				return this.returnUniqueResult(query);
			}
		}.execute();
	}

	
	@Override
	public Product create(final int categoryId, final Product product, final String productName, final Language language) throws ServiceException {
		
		return new SessionExecutor<Product>(factory, "can't create Product ") {
			@Override
			protected Product logic(Session session) throws HibernateException, ServiceException {
				session.beginTransaction();
				session.save(product);
				session.flush();
				session.save(createDescription(session, product.getProductId(), productName, language));
				session.save(createCategoryLink(session, product.getProductId(), categoryId));
				session.save(createStoreLink(product.getProductId()));
				session.getTransaction().commit();
				return product;
			}

			private ProductToStore createStoreLink(Integer ProductId) {
				ProductToStore returnValue=new ProductToStore();
				returnValue.setId(new ProductToStoreId(ProductId, 0));
				return returnValue;
			}

			private ProductToCategory createCategoryLink(Session session, Integer productId, int categoryId) {
				ProductToCategory returnValue=new ProductToCategory();
				returnValue.setId(new ProductToCategoryId(productId, categoryId));
				return returnValue;
			}
			
			private ProductDescription createDescription(Session session, Integer ProductId, String ProductName, Language language) throws ServiceException{
				ProductDescription ProductDescription=new ProductDescription();
				ProductDescription.setName(ProductName);
				ProductDescription.setId(new ProductDescriptionId(ProductId, language.getLanguageId()));
				ProductDescription.setDescription(StringUtils.EMPTY);
				ProductDescription.setMetaDescription(StringUtils.EMPTY);
				ProductDescription.setMetaKeyword(StringUtils.EMPTY);
				return ProductDescription;
			}
		}.execute();
	}


	
}
